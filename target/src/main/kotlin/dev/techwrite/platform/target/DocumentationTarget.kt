package dev.techwrite.platform.target

import dev.techwrite.platform.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "documentation_targets")
open class DocumentationTarget protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 120)
    lateinit var name: String
        protected set

    @Column(nullable = false)
    var targetCoverage: Int = 0
        protected set

    @Column(nullable = false)
    var reviewSlaHours: Int = 0
        protected set

    @Column(nullable = false)
    lateinit var targetDate: LocalDate
        protected set

    @Column(nullable = false)
    var active: Boolean = false
        protected set

    constructor(
        ownerId: String,
        name: String,
        targetCoverage: Int,
        reviewSlaHours: Int,
        targetDate: LocalDate,
        active: Boolean,
    ) : this() {
        this.ownerId = ownerId
        this.name = name
        this.targetCoverage = targetCoverage
        this.reviewSlaHours = reviewSlaHours
        this.targetDate = targetDate
        this.active = active
    }
}
