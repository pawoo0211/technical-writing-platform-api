package dev.techwrite.platform.workspace

import dev.techwrite.platform.common.OwnedEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "documentation_workspaces")
open class DocumentationWorkspace protected constructor() : OwnedEntity() {

    @Column(nullable = false, length = 100)
    lateinit var name: String
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    lateinit var type: WorkspaceType
        protected set

    @Column(nullable = false, length = 100)
    lateinit var owningTeam: String
        protected set

    constructor(ownerId: String, name: String, type: WorkspaceType, owningTeam: String) : this() {
        this.ownerId = ownerId
        this.name = name
        this.type = type
        this.owningTeam = owningTeam
    }
}
