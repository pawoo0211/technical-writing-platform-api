package dev.techwrite.platform.review

import dev.techwrite.platform.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "review_issues")
open class ReviewIssue protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 160)
    lateinit var documentTitle: String
        protected set

    @Column(nullable = false, length = 80)
    lateinit var category: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    lateinit var severity: ReviewSeverity
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    lateinit var status: ReviewStatus
        protected set

    @Column(nullable = false, length = 240)
    lateinit var message: String
        protected set

    constructor(
        ownerId: String,
        documentTitle: String,
        category: String,
        severity: ReviewSeverity,
        status: ReviewStatus,
        message: String,
    ) : this() {
        this.ownerId = ownerId
        this.documentTitle = documentTitle
        this.category = category
        this.severity = severity
        this.status = status
        this.message = message
    }
}
