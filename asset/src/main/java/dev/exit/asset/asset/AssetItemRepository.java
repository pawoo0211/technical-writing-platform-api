package dev.exit.asset.asset;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.asset.AssetItem;

public interface AssetItemRepository extends JpaRepository<AssetItem, UUID> {

	List<AssetItem> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}
