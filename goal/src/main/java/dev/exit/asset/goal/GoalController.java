package dev.exit.asset.goal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.exit.asset.common.CurrentUserProvider;
import dev.exit.asset.goal.ExitGoal;
import dev.exit.asset.goal.ExitGoalRepository;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

	private final CurrentUserProvider currentUser;

	private final ExitGoalRepository goals;

	public GoalController(CurrentUserProvider currentUser, ExitGoalRepository goals) {
		this.currentUser = currentUser;
		this.goals = goals;
	}

	@GetMapping
	public List<ExitGoal> list() {
		return goals.findByOwnerIdOrderByCreatedAtDesc(currentUser.ownerId());
	}

	@PostMapping
	public ExitGoal create(@Valid @RequestBody CreateGoalRequest request) {
		return goals.save(new ExitGoal(currentUser.ownerId(), request.name(), request.targetNetWorth(),
				request.targetDate(), request.targetSavingsRate(), request.active()));
	}

	public record CreateGoalRequest(@NotBlank String name, @NotNull @Positive BigDecimal targetNetWorth,
			@NotNull @FutureOrPresent LocalDate targetDate,
			@NotNull @PositiveOrZero BigDecimal targetSavingsRate, boolean active) {
	}
}
