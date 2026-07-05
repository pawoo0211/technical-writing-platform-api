package dev.techwrite.platform.workspace

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DocumentationWorkspaceRepository : JpaRepository<DocumentationWorkspace, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<DocumentationWorkspace>
}
