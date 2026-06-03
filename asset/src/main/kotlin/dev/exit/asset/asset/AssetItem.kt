package dev.exit.asset.asset

import dev.exit.asset.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "asset_items")
open class AssetItem protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 120)
    lateinit var name: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    lateinit var type: AssetType
        protected set

    @Column(nullable = false, precision = 19, scale = 2)
    lateinit var currentValue: BigDecimal
        protected set

    @Column(nullable = false, length = 3)
    lateinit var currency: String
        protected set

    constructor(ownerId: String, name: String, type: AssetType, currentValue: BigDecimal, currency: String) : this() {
        this.ownerId = ownerId
        this.name = name
        this.type = type
        this.currentValue = currentValue
        this.currency = currency
    }
}
