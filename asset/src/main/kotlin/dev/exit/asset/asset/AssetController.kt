package dev.exit.asset.asset

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
@RequestMapping("/api/v1/assets")
class AssetController(
    private val currentUser: CurrentUserProvider,
    private val assets: AssetItemRepository,
) {

    @GetMapping
    fun list(): List<AssetItem> = assets.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateAssetRequest): AssetItem =
        assets.save(AssetItem(currentUser.ownerId(), request.name, request.type, request.currentValue, request.currency))

    data class CreateAssetRequest(
        @field:NotBlank val name: String,
        @field:NotNull val type: AssetType,
        @field:NotNull @field:PositiveOrZero val currentValue: BigDecimal,
        @field:NotBlank val currency: String,
    )
}
