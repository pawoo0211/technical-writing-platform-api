package dev.exit.asset.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.domain.AssetItem;

public interface AssetItemRepository extends JpaRepository<AssetItem, UUID> {

	List<AssetItem> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}
