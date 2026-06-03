package dev.exit.asset.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.domain.AssetAccount;

public interface AssetAccountRepository extends JpaRepository<AssetAccount, UUID> {

	List<AssetAccount> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}
