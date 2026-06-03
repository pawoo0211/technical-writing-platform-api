package dev.exit.asset.asset;

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

import dev.exit.asset.common.CurrentUserProvider;
import dev.exit.asset.asset.AssetItem;
import dev.exit.asset.asset.AssetType;
import dev.exit.asset.asset.AssetItemRepository;

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
