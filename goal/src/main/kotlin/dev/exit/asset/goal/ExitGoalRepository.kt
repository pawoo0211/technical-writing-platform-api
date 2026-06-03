package dev.exit.asset.goal

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ExitGoalRepository : JpaRepository<ExitGoal, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<ExitGoal>

    fun findFirstByOwnerIdAndActiveTrueOrderByCreatedAtDesc(ownerId: String): Optional<ExitGoal>
}
