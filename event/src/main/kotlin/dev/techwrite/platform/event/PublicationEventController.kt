package dev.techwrite.platform.event

import dev.techwrite.platform.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/events")
class PublicationEventController(
    private val currentUser: CurrentUserProvider,
    private val events: PublicationEventRepository,
) {

    @GetMapping
    fun list(): List<PublicationEvent> = events.findByOwnerIdOrderByOccurredOnDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreatePublicationEventRequest): PublicationEvent =
        events.save(
            PublicationEvent(
                currentUser.ownerId(),
                request.occurredOn,
                request.type,
                request.documentTitle,
                request.actor,
                request.note,
            ),
        )

    data class CreatePublicationEventRequest(
        @field:NotNull val occurredOn: LocalDate,
        @field:NotNull val type: PublicationEventType,
        @field:NotBlank val documentTitle: String,
        @field:NotBlank val actor: String,
        @field:NotBlank val note: String,
    )
}
