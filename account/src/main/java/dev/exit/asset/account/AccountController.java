package dev.exit.asset.account;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.exit.asset.common.CurrentUserProvider;
import dev.exit.asset.account.AccountKind;
import dev.exit.asset.account.AssetAccount;
import dev.exit.asset.account.AssetAccountRepository;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final CurrentUserProvider currentUser;

	private final AssetAccountRepository accounts;

	public AccountController(CurrentUserProvider currentUser, AssetAccountRepository accounts) {
		this.currentUser = currentUser;
		this.accounts = accounts;
	}

	@GetMapping
	public List<AssetAccount> list() {
		return accounts.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId());
	}

	@PostMapping
	public AssetAccount create(@Valid @RequestBody CreateAccountRequest request) {
		return accounts.save(new AssetAccount(currentUser.ownerId(), request.name(), request.kind(), request.institution()));
	}

	public record CreateAccountRequest(@NotBlank String name, @NotNull AccountKind kind, @NotBlank String institution) {
	}
}
