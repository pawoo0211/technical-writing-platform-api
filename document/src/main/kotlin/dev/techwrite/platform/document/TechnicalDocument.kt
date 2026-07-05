package dev.techwrite.platform.document

import dev.techwrite.platform.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "technical_documents")
open class TechnicalDocument protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 160)
    lateinit var title: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    lateinit var type: DocumentType
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    lateinit var status: DocumentStatus
        protected set

    @Column(nullable = false, length = 120)
    lateinit var workspaceName: String
        protected set

    @Column(nullable = false, length = 120)
    lateinit var author: String
        protected set

    @Column(nullable = false)
    var freshnessScore: Int = 0
        protected set

    constructor(
        ownerId: String,
        title: String,
        type: DocumentType,
        status: DocumentStatus,
        workspaceName: String,
        author: String,
        freshnessScore: Int,
    ) : this() {
        this.ownerId = ownerId
        this.title = title
        this.type = type
        this.status = status
        this.workspaceName = workspaceName
        this.author = author
        this.freshnessScore = freshnessScore
    }
}
