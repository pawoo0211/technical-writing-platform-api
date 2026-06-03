package dev.exit.asset.cashflow

import dev.exit.asset.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "cash_flow_transactions")
open class CashFlowTransaction protected constructor() : OwnedEntity() {

    @Column(nullable = false)
    lateinit var transactedOn: LocalDate
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    lateinit var type: TransactionType
        protected set

    @Column(nullable = false, length = 80)
    lateinit var category: String
        protected set

    @Column(nullable = false, length = 160)
    lateinit var memo: String
        protected set

    @Column(nullable = false, precision = 19, scale = 2)
    lateinit var amount: BigDecimal
        protected set

    constructor(
        ownerId: String,
        transactedOn: LocalDate,
        type: TransactionType,
        category: String,
        memo: String,
        amount: BigDecimal,
    ) : this() {
        this.ownerId = ownerId
        this.transactedOn = transactedOn
        this.type = type
        this.category = category
        this.memo = memo
        this.amount = amount
    }
}
