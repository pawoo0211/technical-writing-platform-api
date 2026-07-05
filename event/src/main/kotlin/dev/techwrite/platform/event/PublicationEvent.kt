package dev.techwrite.platform.event

import dev.techwrite.platform.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "publication_events")
open class PublicationEvent protected constructor() : OwnedEntity() {

    @Column(nullable = false)
    lateinit var occurredOn: LocalDate
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    lateinit var type: PublicationEventType
        protected set

    @Column(nullable = false, length = 160)
    lateinit var documentTitle: String
        protected set

    @Column(nullable = false, length = 120)
    lateinit var actor: String
        protected set

    @Column(nullable = false, length = 240)
    lateinit var note: String
        protected set

    constructor(
        ownerId: String,
        occurredOn: LocalDate,
        type: PublicationEventType,
        documentTitle: String,
        actor: String,
        note: String,
    ) : this() {
        this.ownerId = ownerId
        this.occurredOn = occurredOn
        this.type = type
        this.documentTitle = documentTitle
        this.actor = actor
        this.note = note
    }
}
