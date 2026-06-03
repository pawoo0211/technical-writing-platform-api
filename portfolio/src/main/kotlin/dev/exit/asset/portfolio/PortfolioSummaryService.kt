package dev.exit.asset.portfolio

import dev.exit.asset.asset.AssetItem
import dev.exit.asset.asset.AssetItemRepository
import dev.exit.asset.cashflow.CashFlowTransaction
import dev.exit.asset.cashflow.CashFlowTransactionRepository
import dev.exit.asset.cashflow.TransactionType
import dev.exit.asset.goal.ExitGoalRepository
import dev.exit.asset.liability.Liability
import dev.exit.asset.liability.LiabilityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.YearMonth

@Service
class PortfolioSummaryService(
    private val assets: AssetItemRepository,
    private val liabilities: LiabilityRepository,
    private val transactions: CashFlowTransactionRepository,
    private val goals: ExitGoalRepository,
) {

    @Transactional(readOnly = true)
    fun summarize(ownerId: String, month: YearMonth): PortfolioSummary {
        val totalAssets = assets.findByOwnerIdOrderByCreatedAtDesc(ownerId)
            .map(AssetItem::currentValue)
            .fold(BigDecimal.ZERO, BigDecimal::add)
        val totalLiabilities = liabilities.findByOwnerIdOrderByCreatedAtDesc(ownerId)
            .map(Liability::outstandingBalance)
            .fold(BigDecimal.ZERO, BigDecimal::add)
        val netWorth = totalAssets.subtract(totalLiabilities)

        val monthlyTransactions = transactions.findByOwnerIdAndTransactedOnBetween(
            ownerId,
            month.atDay(1),
            month.atEndOfMonth(),
        )
        val income = sumByType(monthlyTransactions, TransactionType.INCOME)
        val expense = sumByType(monthlyTransactions, TransactionType.EXPENSE)
        val savings = income.subtract(expense)
        val savingsRate = if (income.signum() == 0) {
            BigDecimal.ZERO
        } else {
            savings.multiply(BigDecimal.valueOf(100)).divide(income, 2, RoundingMode.HALF_UP)
        }

        val activeGoal = goals.findFirstByOwnerIdAndActiveTrueOrderByCreatedAtDesc(ownerId).orElse(null)
        val goalProgress = if (activeGoal == null || activeGoal.targetNetWorth.signum() == 0) {
            BigDecimal.ZERO
        } else {
            netWorth.multiply(BigDecimal.valueOf(100)).divide(activeGoal.targetNetWorth, 2, RoundingMode.HALF_UP)
        }
        val monthsToExit = if (activeGoal == null || savings.signum() <= 0) {
            null
        } else {
            activeGoal.targetNetWorth
                .subtract(netWorth)
                .max(BigDecimal.ZERO)
                .divide(savings, 0, RoundingMode.CEILING)
                .toInt()
        }

        return PortfolioSummary(
            totalAssets,
            totalLiabilities,
            netWorth,
            income,
            expense,
            savings,
            savingsRate,
            goalProgress,
            monthsToExit,
        )
    }

    private fun sumByType(items: Iterable<CashFlowTransaction>, type: TransactionType): BigDecimal =
        items
            .filter { it.type == type }
            .fold(BigDecimal.ZERO) { total, item -> total.add(item.amount) }

    data class PortfolioSummary(
        val totalAssets: BigDecimal,
        val totalLiabilities: BigDecimal,
        val netWorth: BigDecimal,
        val monthlyIncome: BigDecimal,
        val monthlyExpense: BigDecimal,
        val monthlySavings: BigDecimal,
        val savingsRate: BigDecimal,
        val goalProgress: BigDecimal,
        val monthsToExit: Int?,
    )
}
