package dev.exit.asset.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "exit_goals")
public class ExitGoal extends OwnedEntity {

	@Column(nullable = false, length = 120)
	private String name;

	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal targetNetWorth;

	@Column(nullable = false)
	private LocalDate targetDate;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal targetSavingsRate;

	@Column(nullable = false)
	private boolean active;

	protected ExitGoal() {
	}

	public ExitGoal(String ownerId, String name, BigDecimal targetNetWorth, LocalDate targetDate,
			BigDecimal targetSavingsRate, boolean active) {
		super(ownerId);
		this.name = name;
		this.targetNetWorth = targetNetWorth;
		this.targetDate = targetDate;
		this.targetSavingsRate = targetSavingsRate;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getTargetNetWorth() {
		return targetNetWorth;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public BigDecimal getTargetSavingsRate() {
		return targetSavingsRate;
	}

	public boolean isActive() {
		return active;
	}
}
