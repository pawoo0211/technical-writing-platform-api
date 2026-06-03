package dev.exit.asset.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "cash_flow_transactions")
public class CashFlowTransaction extends OwnedEntity {

	@Column(nullable = false)
	private LocalDate transactedOn;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TransactionType type;

	@Column(nullable = false, length = 80)
	private String category;

	@Column(nullable = false, length = 160)
	private String memo;

	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	protected CashFlowTransaction() {
	}

	public CashFlowTransaction(String ownerId, LocalDate transactedOn, TransactionType type, String category,
			String memo, BigDecimal amount) {
		super(ownerId);
		this.transactedOn = transactedOn;
		this.type = type;
		this.category = category;
		this.memo = memo;
		this.amount = amount;
	}

	public LocalDate getTransactedOn() {
		return transactedOn;
	}

	public TransactionType getType() {
		return type;
	}

	public String getCategory() {
		return category;
	}

	public String getMemo() {
		return memo;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
