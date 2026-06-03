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
import dev.exit.asset.domain.Liability;
import dev.exit.asset.domain.LiabilityType;
import dev.exit.asset.portfolio.LiabilityRepository;

@RestController
@RequestMapping("/api/v1/liabilities")
public class LiabilityController {

	private final CurrentUserProvider currentUser;

	private final LiabilityRepository liabilities;

	public LiabilityController(CurrentUserProvider currentUser, LiabilityRepository liabilities) {
		this.currentUser = currentUser;
		this.liabilities = liabilities;
	}

	@GetMapping
	public List<Liability> list() {
		return liabilities.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId());
	}

	@PostMapping
	public Liability create(@Valid @RequestBody CreateLiabilityRequest request) {
		return liabilities.save(new Liability(currentUser.ownerId(), request.name(), request.type(),
				request.outstandingBalance(), request.annualInterestRate()));
	}

	public record CreateLiabilityRequest(@NotBlank String name, @NotNull LiabilityType type,
			@NotNull @PositiveOrZero BigDecimal outstandingBalance,
			@NotNull @PositiveOrZero BigDecimal annualInterestRate) {
	}
}
