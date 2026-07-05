package dev.techwrite.platform.document

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TechnicalDocumentRepository : JpaRepository<TechnicalDocument, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<TechnicalDocument>
}
