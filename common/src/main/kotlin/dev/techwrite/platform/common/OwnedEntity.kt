package dev.techwrite.platform.common

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.OffsetDateTime
import java.util.UUID

@MappedSuperclass
abstract class OwnedEntity protected constructor() {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
        protected set

    @Column(nullable = false, length = 128)
    lateinit var ownerId: String
        protected set

    @Column(nullable = false)
    lateinit var createdAt: OffsetDateTime
        protected set

    @Column(nullable = false)
    lateinit var updatedAt: OffsetDateTime
        protected set

    @PrePersist
    fun prePersist() {
        val now = OffsetDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = OffsetDateTime.now()
    }
}
