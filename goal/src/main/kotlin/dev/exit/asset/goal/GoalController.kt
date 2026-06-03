package dev.exit.asset.goal

import dev.exit.asset.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/goals")
class GoalController(
    private val currentUser: CurrentUserProvider,
    private val goals: ExitGoalRepository,
) {

    @GetMapping
    fun list(): List<ExitGoal> = goals.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateGoalRequest): ExitGoal =
        goals.save(
            ExitGoal(
                currentUser.ownerId(),
                request.name,
                request.targetNetWorth,
                request.targetDate,
                request.targetSavingsRate,
                request.active,
            ),
        )

    data class CreateGoalRequest(
        @field:NotBlank val name: String,
        @field:NotNull @field:Positive val targetNetWorth: BigDecimal,
        @field:NotNull @field:FutureOrPresent val targetDate: LocalDate,
        @field:NotNull @field:PositiveOrZero val targetSavingsRate: BigDecimal,
        val active: Boolean,
    )
}
