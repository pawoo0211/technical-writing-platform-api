package dev.exit.asset.account

import dev.exit.asset.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "asset_accounts")
open class AssetAccount protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 100)
    lateinit var name: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    lateinit var kind: AccountKind
        protected set

    @Column(nullable = false, length = 80)
    lateinit var institution: String
        protected set

    constructor(ownerId: String, name: String, kind: AccountKind, institution: String) : this() {
        this.ownerId = ownerId
        this.name = name
        this.kind = kind
        this.institution = institution
    }
}
