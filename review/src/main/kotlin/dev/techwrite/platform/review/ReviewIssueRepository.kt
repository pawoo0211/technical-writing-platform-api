package dev.techwrite.platform.review

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReviewIssueRepository : JpaRepository<ReviewIssue, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<ReviewIssue>
}
