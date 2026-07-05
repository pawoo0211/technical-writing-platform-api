package dev.techwrite.platform.target

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface DocumentationTargetRepository : JpaRepository<DocumentationTarget, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<DocumentationTarget>

    fun findFirstByOwnerIdAndActiveTrueOrderByCreatedAtDesc(ownerId: String): Optional<DocumentationTarget>
}
