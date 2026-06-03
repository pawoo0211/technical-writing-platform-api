package dev.exit.asset.account

import dev.exit.asset.common.CurrentUserProvider
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val currentUser: CurrentUserProvider,
    private val accounts: AssetAccountRepository,
) {

    @GetMapping
    fun list(): List<AssetAccount> = accounts.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateAccountRequest): AssetAccount =
        accounts.save(AssetAccount(currentUser.ownerId(), request.name, request.kind, request.institution))

    data class CreateAccountRequest(
        @field:NotBlank val name: String,
        @field:NotNull val kind: AccountKind,
        @field:NotBlank val institution: String,
    )
}
