package dev.exit.asset.cashflow

import dev.exit.asset.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(
    private val currentUser: CurrentUserProvider,
    private val transactions: CashFlowTransactionRepository,
) {

    @GetMapping
    fun list(): List<CashFlowTransaction> = transactions.findByOwnerIdOrderByTransactedOnDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateTransactionRequest): CashFlowTransaction =
        transactions.save(
            CashFlowTransaction(
                currentUser.ownerId(),
                request.transactedOn,
                request.type,
                request.category,
                request.memo,
                request.amount,
            ),
        )

    data class CreateTransactionRequest(
        @field:NotNull val transactedOn: LocalDate,
        @field:NotNull val type: TransactionType,
        @field:NotBlank val category: String,
        @field:NotBlank val memo: String,
        @field:NotNull @field:Positive val amount: BigDecimal,
    )
}
