package dev.exit.asset.account;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.account.AssetAccount;

public interface AssetAccountRepository extends JpaRepository<AssetAccount, UUID> {

	List<AssetAccount> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}
