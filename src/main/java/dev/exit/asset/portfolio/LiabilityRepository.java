package dev.exit.asset.portfolio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.domain.Liability;

public interface LiabilityRepository extends JpaRepository<Liability, UUID> {

	List<Liability> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}
