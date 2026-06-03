package dev.exit.asset.liability

import dev.exit.asset.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/liabilities")
class LiabilityController(
    private val currentUser: CurrentUserProvider,
    private val liabilities: LiabilityRepository,
) {

    @GetMapping
    fun list(): List<Liability> = liabilities.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateLiabilityRequest): Liability =
        liabilities.save(
            Liability(
                currentUser.ownerId(),
                request.name,
                request.type,
                request.outstandingBalance,
                request.annualInterestRate,
            ),
        )

    data class CreateLiabilityRequest(
        @field:NotBlank val name: String,
        @field:NotNull val type: LiabilityType,
        @field:NotNull @field:PositiveOrZero val outstandingBalance: BigDecimal,
        @field:NotNull @field:PositiveOrZero val annualInterestRate: BigDecimal,
    )
}
