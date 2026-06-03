package dev.exit.asset.account;

import dev.exit.asset.common.OwnedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "asset_accounts")
public class AssetAccount extends OwnedEntity {

	@Column(nullable = false, length = 100)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private AccountKind kind;

	@Column(nullable = false, length = 80)
	private String institution;

	protected AssetAccount() {
	}

	public AssetAccount(String ownerId, String name, AccountKind kind, String institution) {
		super(ownerId);
		this.name = name;
		this.kind = kind;
		this.institution = institution;
	}

	public String getName() {
		return name;
	}

	public AccountKind getKind() {
		return kind;
	}

	public String getInstitution() {
		return institution;
	}
}
