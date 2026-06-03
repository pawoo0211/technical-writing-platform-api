package dev.exit.asset.goal

import dev.exit.asset.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "exit_goals")
open class ExitGoal protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 120)
    lateinit var name: String
        protected set

    @Column(nullable = false, precision = 19, scale = 2)
    lateinit var targetNetWorth: BigDecimal
        protected set

    @Column(nullable = false)
    lateinit var targetDate: LocalDate
        protected set

    @Column(nullable = false, precision = 5, scale = 2)
    lateinit var targetSavingsRate: BigDecimal
        protected set

    @Column(nullable = false)
    var active: Boolean = false
        protected set

    constructor(
        ownerId: String,
        name: String,
        targetNetWorth: BigDecimal,
        targetDate: LocalDate,
        targetSavingsRate: BigDecimal,
        active: Boolean,
    ) : this() {
        this.ownerId = ownerId
        this.name = name
        this.targetNetWorth = targetNetWorth
        this.targetDate = targetDate
        this.targetSavingsRate = targetSavingsRate
        this.active = active
    }
}
