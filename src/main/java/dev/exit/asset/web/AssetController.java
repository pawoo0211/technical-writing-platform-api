package dev.exit.asset.web;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.exit.asset.config.CurrentUserProvider;
import dev.exit.asset.domain.AssetItem;
import dev.exit.asset.domain.AssetType;
import dev.exit.asset.portfolio.AssetItemRepository;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

	private final CurrentUserProvider currentUser;

	private final AssetItemRepository assets;

	public AssetController(CurrentUserProvider currentUser, AssetItemRepository assets) {
		this.currentUser = currentUser;
		this.assets = assets;
	}

	@GetMapping
	public List<AssetItem> list() {
		return assets.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId());
	}

	@PostMapping
	public AssetItem create(@Valid @RequestBody CreateAssetRequest request) {
		return assets.save(new AssetItem(currentUser.ownerId(), request.name(), request.type(), request.currentValue(),
				request.currency()));
	}

	public record CreateAssetRequest(@NotBlank String name, @NotNull AssetType type,
			@NotNull @PositiveOrZero BigDecimal currentValue, @NotBlank String currency) {
	}
}
