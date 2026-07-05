package dev.techwrite.platform.target

import dev.techwrite.platform.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/targets")
class TargetController(
    private val currentUser: CurrentUserProvider,
    private val targets: DocumentationTargetRepository,
) {

    @GetMapping
    fun list(): List<DocumentationTarget> = targets.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateTargetRequest): DocumentationTarget =
        targets.save(
            DocumentationTarget(
                currentUser.ownerId(),
                request.name,
                request.targetCoverage,
                request.reviewSlaHours,
                request.targetDate,
                request.active,
            ),
        )

    data class CreateTargetRequest(
        @field:NotBlank val name: String,
        @field:Min(0) @field:Max(100) val targetCoverage: Int,
        @field:Positive val reviewSlaHours: Int,
        @field:NotNull @field:FutureOrPresent val targetDate: LocalDate,
        val active: Boolean,
    )
}
