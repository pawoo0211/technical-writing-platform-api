package dev.exit.asset.liability;

import java.math.BigDecimal;

import dev.exit.asset.common.OwnedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "liabilities")
public class Liability extends OwnedEntity {

	@Column(nullable = false, length = 120)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private LiabilityType type;

	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal outstandingBalance;

	@Column(nullable = false, precision = 6, scale = 3)
	private BigDecimal annualInterestRate;

	protected Liability() {
	}

	public Liability(String ownerId, String name, LiabilityType type, BigDecimal outstandingBalance,
			BigDecimal annualInterestRate) {
		super(ownerId);
		this.name = name;
		this.type = type;
		this.outstandingBalance = outstandingBalance;
		this.annualInterestRate = annualInterestRate;
	}

	public String getName() {
		return name;
	}

	public LiabilityType getType() {
		return type;
	}

	public BigDecimal getOutstandingBalance() {
		return outstandingBalance;
	}

	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}
}
