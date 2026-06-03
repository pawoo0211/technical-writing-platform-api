package dev.exit.asset.goal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.goal.ExitGoal;

public interface ExitGoalRepository extends JpaRepository<ExitGoal, UUID> {

	List<ExitGoal> findByOwnerIdOrderByCreatedAtDesc(String ownerId);

	Optional<ExitGoal> findFirstByOwnerIdAndActiveTrueOrderByCreatedAtDesc(String ownerId);
}
