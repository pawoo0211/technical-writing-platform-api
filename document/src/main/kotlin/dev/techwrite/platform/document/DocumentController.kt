package dev.techwrite.platform.document

import dev.techwrite.platform.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/documents")
class DocumentController(
    private val currentUser: CurrentUserProvider,
    private val documents: TechnicalDocumentRepository,
) {

    @GetMapping
    fun list(): List<TechnicalDocument> = documents.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateDocumentRequest): TechnicalDocument =
        documents.save(
            TechnicalDocument(
                currentUser.ownerId(),
                request.title,
                request.type,
                request.status,
                request.workspaceName,
                request.author,
                request.freshnessScore,
            ),
        )

    data class CreateDocumentRequest(
        @field:NotBlank val title: String,
        @field:NotNull val type: DocumentType,
        @field:NotNull val status: DocumentStatus,
        @field:NotBlank val workspaceName: String,
        @field:NotBlank val author: String,
        @field:Min(0) @field:Max(100) val freshnessScore: Int,
    )
}
