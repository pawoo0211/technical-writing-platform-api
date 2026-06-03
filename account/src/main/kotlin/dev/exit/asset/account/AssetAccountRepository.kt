package dev.exit.asset.account

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AssetAccountRepository : JpaRepository<AssetAccount, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<AssetAccount>
}
