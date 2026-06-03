package dev.exit.asset.liability;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.liability.Liability;

public interface LiabilityRepository extends JpaRepository<Liability, UUID> {

	List<Liability> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}
