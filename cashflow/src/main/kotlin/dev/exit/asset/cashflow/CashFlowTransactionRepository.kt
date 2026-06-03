package dev.exit.asset.cashflow

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface CashFlowTransactionRepository : JpaRepository<CashFlowTransaction, UUID> {

    fun findByOwnerIdOrderByTransactedOnDesc(ownerId: String): List<CashFlowTransaction>

    fun findByOwnerIdAndTransactedOnBetween(
        ownerId: String,
        from: LocalDate,
        to: LocalDate,
    ): List<CashFlowTransaction>
}
