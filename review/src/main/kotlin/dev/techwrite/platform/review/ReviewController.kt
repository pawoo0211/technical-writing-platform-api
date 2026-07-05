package dev.techwrite.platform.review

import dev.techwrite.platform.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(
    private val currentUser: CurrentUserProvider,
    private val issues: ReviewIssueRepository,
) {

    @GetMapping
    fun list(): List<ReviewIssue> = issues.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateReviewIssueRequest): ReviewIssue =
        issues.save(
            ReviewIssue(
                currentUser.ownerId(),
                request.documentTitle,
                request.category,
                request.severity,
                request.status,
                request.message,
            ),
        )

    data class CreateReviewIssueRequest(
        @field:NotBlank val documentTitle: String,
        @field:NotBlank val category: String,
        @field:NotNull val severity: ReviewSeverity,
        @field:NotNull val status: ReviewStatus,
        @field:NotBlank val message: String,
    )
}
