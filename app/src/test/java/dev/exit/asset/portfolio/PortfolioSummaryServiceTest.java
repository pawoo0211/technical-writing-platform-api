package dev.exit.asset.portfolio;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import dev.exit.asset.domain.AssetItem;
import dev.exit.asset.domain.AssetType;
import dev.exit.asset.domain.CashFlowTransaction;
import dev.exit.asset.domain.ExitGoal;
import dev.exit.asset.domain.Liability;
import dev.exit.asset.domain.LiabilityType;
import dev.exit.asset.domain.TransactionType;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioSummaryServiceTest {

	@Autowired
	AssetItemRepository assets;

	@Autowired
	LiabilityRepository liabilities;

	@Autowired
	CashFlowTransactionRepository transactions;

	@Autowired
	ExitGoalRepository goals;

	@Autowired
	PortfolioSummaryService summaries;

	@Test
	void summarizesNetWorthCashFlowAndGoalProgress() {
		String ownerId = "demo-user";
		assets.save(new AssetItem(ownerId, "Emergency cash", AssetType.CASH, BigDecimal.valueOf(20_000_000), "KRW"));
		assets.save(new AssetItem(ownerId, "ETF portfolio", AssetType.ETF, BigDecimal.valueOf(50_000_000), "KRW"));
		liabilities.save(new Liability(ownerId, "Credit loan", LiabilityType.CREDIT_LOAN, BigDecimal.valueOf(10_000_000),
				BigDecimal.valueOf(4.5)));
		transactions.save(new CashFlowTransaction(ownerId, LocalDate.of(2026, 6, 1), TransactionType.INCOME, "Salary",
				"June salary", BigDecimal.valueOf(5_000_000)));
		transactions.save(new CashFlowTransaction(ownerId, LocalDate.of(2026, 6, 2), TransactionType.EXPENSE, "Living",
				"Monthly spending", BigDecimal.valueOf(2_000_000)));
		goals.save(new ExitGoal(ownerId, "First Exit", BigDecimal.valueOf(120_000_000), LocalDate.of(2028, 12, 31),
				BigDecimal.valueOf(50), true));

		var summary = summaries.summarize(ownerId, YearMonth.of(2026, 6));

		assertThat(summary.totalAssets()).isEqualByComparingTo("70000000");
		assertThat(summary.totalLiabilities()).isEqualByComparingTo("10000000");
		assertThat(summary.netWorth()).isEqualByComparingTo("60000000");
		assertThat(summary.monthlySavings()).isEqualByComparingTo("3000000");
		assertThat(summary.savingsRate()).isEqualByComparingTo("60.00");
		assertThat(summary.goalProgress()).isEqualByComparingTo("50.00");
		assertThat(summary.monthsToExit()).isEqualTo(20);
	}
}
