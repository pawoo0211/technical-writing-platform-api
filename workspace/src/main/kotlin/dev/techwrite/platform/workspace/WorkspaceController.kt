package dev.techwrite.platform.workspace

import dev.techwrite.platform.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/workspaces")
class WorkspaceController(
    private val currentUser: CurrentUserProvider,
    private val workspaces: DocumentationWorkspaceRepository,
) {

    @GetMapping
    fun list(): List<DocumentationWorkspace> = workspaces.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateWorkspaceRequest): DocumentationWorkspace =
        workspaces.save(DocumentationWorkspace(currentUser.ownerId(), request.name, request.type, request.owningTeam))

    data class CreateWorkspaceRequest(
        @field:NotBlank val name: String,
        @field:NotNull val type: WorkspaceType,
        @field:NotBlank val owningTeam: String,
    )
}
