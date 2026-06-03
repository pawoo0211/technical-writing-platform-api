package dev.exit.asset.portfolio

import dev.exit.asset.asset.AssetItem
import dev.exit.asset.asset.AssetItemRepository
import dev.exit.asset.asset.AssetType
import dev.exit.asset.cashflow.CashFlowTransaction
import dev.exit.asset.cashflow.CashFlowTransactionRepository
import dev.exit.asset.cashflow.TransactionType
import dev.exit.asset.goal.ExitGoal
import dev.exit.asset.goal.ExitGoalRepository
import dev.exit.asset.liability.Liability
import dev.exit.asset.liability.LiabilityRepository
import dev.exit.asset.liability.LiabilityType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.YearMonth

@SpringBootTest
@ActiveProfiles("test")
class PortfolioSummaryServiceTest {

    @Autowired
    lateinit var assets: AssetItemRepository

    @Autowired
    lateinit var liabilities: LiabilityRepository

    @Autowired
    lateinit var transactions: CashFlowTransactionRepository

    @Autowired
    lateinit var goals: ExitGoalRepository

    @Autowired
    lateinit var summaries: PortfolioSummaryService

    @Test
    fun summarizesNetWorthCashFlowAndGoalProgress() {
        val ownerId = "demo-user"
        assets.save(AssetItem(ownerId, "Emergency cash", AssetType.CASH, BigDecimal.valueOf(20_000_000), "KRW"))
        assets.save(AssetItem(ownerId, "ETF portfolio", AssetType.ETF, BigDecimal.valueOf(50_000_000), "KRW"))
        liabilities.save(
            Liability(
                ownerId,
                "Credit loan",
                LiabilityType.CREDIT_LOAN,
                BigDecimal.valueOf(10_000_000),
                BigDecimal.valueOf(4.5),
            ),
        )
        transactions.save(
            CashFlowTransaction(
                ownerId,
                LocalDate.of(2026, 6, 1),
                TransactionType.INCOME,
                "Salary",
                "June salary",
                BigDecimal.valueOf(5_000_000),
            ),
        )
        transactions.save(
            CashFlowTransaction(
                ownerId,
                LocalDate.of(2026, 6, 2),
                TransactionType.EXPENSE,
                "Living",
                "Monthly spending",
                BigDecimal.valueOf(2_000_000),
            ),
        )
        goals.save(
            ExitGoal(
                ownerId,
                "First Exit",
                BigDecimal.valueOf(120_000_000),
                LocalDate.of(2028, 12, 31),
                BigDecimal.valueOf(50),
                true,
            ),
        )

        val summary = summaries.summarize(ownerId, YearMonth.of(2026, 6))

        assertThat(summary.totalAssets).isEqualByComparingTo("70000000")
        assertThat(summary.totalLiabilities).isEqualByComparingTo("10000000")
        assertThat(summary.netWorth).isEqualByComparingTo("60000000")
        assertThat(summary.monthlySavings).isEqualByComparingTo("3000000")
        assertThat(summary.savingsRate).isEqualByComparingTo("60.00")
        assertThat(summary.goalProgress).isEqualByComparingTo("50.00")
        assertThat(summary.monthsToExit).isEqualTo(20)
    }
}
