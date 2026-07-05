package dev.techwrite.platform.ops

import dev.techwrite.platform.document.DocumentStatus
import dev.techwrite.platform.document.DocumentType
import dev.techwrite.platform.document.TechnicalDocument
import dev.techwrite.platform.document.TechnicalDocumentRepository
import dev.techwrite.platform.event.PublicationEvent
import dev.techwrite.platform.event.PublicationEventRepository
import dev.techwrite.platform.event.PublicationEventType
import dev.techwrite.platform.review.ReviewIssue
import dev.techwrite.platform.review.ReviewIssueRepository
import dev.techwrite.platform.review.ReviewSeverity
import dev.techwrite.platform.review.ReviewStatus
import dev.techwrite.platform.target.DocumentationTarget
import dev.techwrite.platform.target.DocumentationTargetRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.time.YearMonth

@SpringBootTest
@ActiveProfiles("test")
class DocumentationOpsSummaryServiceTest {

    @Autowired
    lateinit var documents: TechnicalDocumentRepository

    @Autowired
    lateinit var reviews: ReviewIssueRepository

    @Autowired
    lateinit var events: PublicationEventRepository

    @Autowired
    lateinit var targets: DocumentationTargetRepository

    @Autowired
    lateinit var summaries: DocumentationOpsSummaryService

    @Test
    fun summarizesDocumentationOperations() {
        val ownerId = "demo-user"
        documents.save(
            TechnicalDocument(
                ownerId,
                "Webhook delivery API",
                DocumentType.API_REFERENCE,
                DocumentStatus.PUBLISHED,
                "API Reference",
                "Platform Engineering",
                90,
            ),
        )
        documents.save(
            TechnicalDocument(
                ownerId,
                "Billing migration guide",
                DocumentType.GUIDE,
                DocumentStatus.IN_REVIEW,
                "Guides",
                "Product Ops",
                70,
            ),
        )
        reviews.save(
            ReviewIssue(
                ownerId,
                "Billing migration guide",
                "operability",
                ReviewSeverity.BLOCKER,
                ReviewStatus.OPEN,
                "Rollback guidance is missing.",
            ),
        )
        events.save(
            PublicationEvent(
                ownerId,
                LocalDate.of(2026, 7, 1),
                PublicationEventType.PUBLISHED,
                "Webhook delivery API",
                "Jin",
                "Published API reference refresh.",
            ),
        )
        events.save(
            PublicationEvent(
                ownerId,
                LocalDate.of(2026, 7, 2),
                PublicationEventType.REVIEW_REQUESTED,
                "Billing migration guide",
                "Park",
                "Requested technical review.",
            ),
        )
        targets.save(
            DocumentationTarget(
                ownerId,
                "Q3 documentation quality",
                85,
                48,
                LocalDate.of(2026, 9, 30),
                true,
            ),
        )

        val summary = summaries.summarize(ownerId, YearMonth.of(2026, 7))

        assertThat(summary.totalDocuments).isEqualTo(2)
        assertThat(summary.publishedDocuments).isEqualTo(1)
        assertThat(summary.coverage).isEqualByComparingTo("50.00")
        assertThat(summary.averageFreshness).isEqualByComparingTo("80.00")
        assertThat(summary.openReviewIssues).isEqualTo(1)
        assertThat(summary.blockerIssues).isEqualTo(1)
        assertThat(summary.publishedThisMonth).isEqualTo(1)
        assertThat(summary.reviewRequestsThisMonth).isEqualTo(1)
        assertThat(summary.targetCoverage).isEqualTo(85)
        assertThat(summary.coverageGap).isEqualByComparingTo("35.00")
    }
}
