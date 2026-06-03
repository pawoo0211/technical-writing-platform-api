package dev.exit.asset.liability

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LiabilityRepository : JpaRepository<Liability, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<Liability>
}
