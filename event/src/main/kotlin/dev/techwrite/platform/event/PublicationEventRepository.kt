package dev.techwrite.platform.event

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface PublicationEventRepository : JpaRepository<PublicationEvent, UUID> {

    fun findByOwnerIdOrderByOccurredOnDesc(ownerId: String): List<PublicationEvent>

    fun findByOwnerIdAndOccurredOnBetween(
        ownerId: String,
        from: LocalDate,
        to: LocalDate,
    ): List<PublicationEvent>
}
