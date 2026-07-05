package dev.techwrite.platform.ops

import dev.techwrite.platform.document.DocumentStatus
import dev.techwrite.platform.document.TechnicalDocumentRepository
import dev.techwrite.platform.event.PublicationEventRepository
import dev.techwrite.platform.event.PublicationEventType
import dev.techwrite.platform.review.ReviewIssueRepository
import dev.techwrite.platform.review.ReviewSeverity
import dev.techwrite.platform.review.ReviewStatus
import dev.techwrite.platform.target.DocumentationTargetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.YearMonth

@Service
class DocumentationOpsSummaryService(
    private val documents: TechnicalDocumentRepository,
    private val reviews: ReviewIssueRepository,
    private val events: PublicationEventRepository,
    private val targets: DocumentationTargetRepository,
) {

    @Transactional(readOnly = true)
    fun summarize(ownerId: String, month: YearMonth): DocumentationOpsSummary {
        val ownedDocuments = documents.findByOwnerIdOrderByCreatedAtDesc(ownerId)
        val totalDocuments = ownedDocuments.size
        val publishedDocuments = ownedDocuments.count { it.status == DocumentStatus.PUBLISHED }
        val coverage = percentage(publishedDocuments, totalDocuments)
        val averageFreshness = if (ownedDocuments.isEmpty()) {
            BigDecimal.ZERO
        } else {
            ownedDocuments
                .map { BigDecimal.valueOf(it.freshnessScore.toLong()) }
                .fold(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(ownedDocuments.size.toLong()), 2, RoundingMode.HALF_UP)
        }

        val ownedReviews = reviews.findByOwnerIdOrderByCreatedAtDesc(ownerId)
        val openReviewIssues = ownedReviews.count { it.status == ReviewStatus.OPEN || it.status == ReviewStatus.IN_PROGRESS }
        val blockerIssues = ownedReviews.count { it.severity == ReviewSeverity.BLOCKER && it.status != ReviewStatus.RESOLVED }

        val monthlyEvents = events.findByOwnerIdAndOccurredOnBetween(ownerId, month.atDay(1), month.atEndOfMonth())
        val publishedThisMonth = monthlyEvents.count { it.type == PublicationEventType.PUBLISHED }
        val reviewRequestsThisMonth = monthlyEvents.count { it.type == PublicationEventType.REVIEW_REQUESTED }

        val activeTarget = targets.findFirstByOwnerIdAndActiveTrueOrderByCreatedAtDesc(ownerId).orElse(null)
        val targetCoverage = activeTarget?.targetCoverage ?: 0
        val coverageGap = BigDecimal.valueOf(targetCoverage.toLong()).subtract(coverage).max(BigDecimal.ZERO)

        return DocumentationOpsSummary(
            totalDocuments = totalDocuments,
            publishedDocuments = publishedDocuments,
            coverage = coverage,
            averageFreshness = averageFreshness,
            openReviewIssues = openReviewIssues,
            blockerIssues = blockerIssues,
            publishedThisMonth = publishedThisMonth,
            reviewRequestsThisMonth = reviewRequestsThisMonth,
            targetCoverage = targetCoverage,
            coverageGap = coverageGap,
        )
    }

    private fun percentage(part: Int, total: Int): BigDecimal {
        if (total == 0) {
            return BigDecimal.ZERO
        }
        return BigDecimal.valueOf(part.toLong())
            .multiply(BigDecimal.valueOf(100))
            .divide(BigDecimal.valueOf(total.toLong()), 2, RoundingMode.HALF_UP)
    }

    data class DocumentationOpsSummary(
        val totalDocuments: Int,
        val publishedDocuments: Int,
        val coverage: BigDecimal,
        val averageFreshness: BigDecimal,
        val openReviewIssues: Int,
        val blockerIssues: Int,
        val publishedThisMonth: Int,
        val reviewRequestsThisMonth: Int,
        val targetCoverage: Int,
        val coverageGap: BigDecimal,
    )
}
