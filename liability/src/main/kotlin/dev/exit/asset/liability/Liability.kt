package dev.exit.asset.liability

import dev.exit.asset.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "liabilities")
open class Liability protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 120)
    lateinit var name: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    lateinit var type: LiabilityType
        protected set

    @Column(nullable = false, precision = 19, scale = 2)
    lateinit var outstandingBalance: BigDecimal
        protected set

    @Column(nullable = false, precision = 6, scale = 3)
    lateinit var annualInterestRate: BigDecimal
        protected set

    constructor(
        ownerId: String,
        name: String,
        type: LiabilityType,
        outstandingBalance: BigDecimal,
        annualInterestRate: BigDecimal,
    ) : this() {
        this.ownerId = ownerId
        this.name = name
        this.type = type
        this.outstandingBalance = outstandingBalance
        this.annualInterestRate = annualInterestRate
    }
}
