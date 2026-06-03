package dev.exit.asset.portfolio;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.exit.asset.domain.CashFlowTransaction;

public interface CashFlowTransactionRepository extends JpaRepository<CashFlowTransaction, UUID> {

	List<CashFlowTransaction> findByOwnerIdOrderByTransactedOnDesc(String ownerId);

	List<CashFlowTransaction> findByOwnerIdAndTransactedOnBetween(String ownerId, LocalDate from, LocalDate to);
}
