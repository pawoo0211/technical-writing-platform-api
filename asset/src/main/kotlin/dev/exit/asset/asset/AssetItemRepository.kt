package dev.exit.asset.asset

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AssetItemRepository : JpaRepository<AssetItem, UUID> {

    fun findByOwnerIdOrderByCreatedAtDesc(ownerId: String): List<AssetItem>
}
