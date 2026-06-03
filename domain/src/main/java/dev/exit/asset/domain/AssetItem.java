package dev.exit.asset.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "asset_items")
public class AssetItem extends OwnedEntity {

	@Column(nullable = false, length = 120)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private AssetType type;

	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal currentValue;

	@Column(nullable = false, length = 3)
	private String currency;

	protected AssetItem() {
	}

	public AssetItem(String ownerId, String name, AssetType type, BigDecimal currentValue, String currency) {
		super(ownerId);
		this.name = name;
		this.type = type;
		this.currentValue = currentValue;
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public AssetType getType() {
		return type;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public String getCurrency() {
		return currency;
	}
}
