package dev.exit.asset.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.exit.asset.domain.AssetItem;
import dev.exit.asset.domain.CashFlowTransaction;
import dev.exit.asset.domain.ExitGoal;
import dev.exit.asset.domain.Liability;
import dev.exit.asset.domain.TransactionType;

@Service
public class PortfolioSummaryService {

	private final AssetItemRepository assets;

	private final LiabilityRepository liabilities;

	private final CashFlowTransactionRepository transactions;

	private final ExitGoalRepository goals;

	public PortfolioSummaryService(AssetItemRepository assets, LiabilityRepository liabilities,
			CashFlowTransactionRepository transactions, ExitGoalRepository goals) {
		this.assets = assets;
		this.liabilities = liabilities;
		this.transactions = transactions;
		this.goals = goals;
	}

	@Transactional(readOnly = true)
	public PortfolioSummary summarize(String ownerId, YearMonth month) {
		BigDecimal totalAssets = assets.findByOwnerIdOrderByCreatedAtDesc(ownerId).stream()
			.map(AssetItem::getCurrentValue)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalLiabilities = liabilities.findByOwnerIdOrderByCreatedAtDesc(ownerId).stream()
			.map(Liability::getOutstandingBalance)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal netWorth = totalAssets.subtract(totalLiabilities);

		var monthlyTransactions = transactions.findByOwnerIdAndTransactedOnBetween(ownerId, month.atDay(1),
				month.atEndOfMonth());
		BigDecimal income = sumByType(monthlyTransactions, TransactionType.INCOME);
		BigDecimal expense = sumByType(monthlyTransactions, TransactionType.EXPENSE);
		BigDecimal savings = income.subtract(expense);
		BigDecimal savingsRate = income.signum() == 0 ? BigDecimal.ZERO
				: savings.multiply(BigDecimal.valueOf(100)).divide(income, 2, RoundingMode.HALF_UP);

		ExitGoal activeGoal = goals.findFirstByOwnerIdAndActiveTrueOrderByCreatedAtDesc(ownerId).orElse(null);
		BigDecimal goalProgress = activeGoal == null || activeGoal.getTargetNetWorth().signum() == 0 ? BigDecimal.ZERO
				: netWorth.multiply(BigDecimal.valueOf(100)).divide(activeGoal.getTargetNetWorth(), 2,
						RoundingMode.HALF_UP);
		Integer monthsToExit = activeGoal == null || savings.signum() <= 0 ? null
				: activeGoal.getTargetNetWorth().subtract(netWorth).max(BigDecimal.ZERO)
					.divide(savings, 0, RoundingMode.CEILING)
					.intValue();

		return new PortfolioSummary(totalAssets, totalLiabilities, netWorth, income, expense, savings, savingsRate,
				goalProgress, monthsToExit);
	}

	private BigDecimal sumByType(Iterable<CashFlowTransaction> items, TransactionType type) {
		BigDecimal total = BigDecimal.ZERO;
		for (CashFlowTransaction item : items) {
			if (item.getType() == type) {
				total = total.add(item.getAmount());
			}
		}
		return total;
	}

	public record PortfolioSummary(BigDecimal totalAssets, BigDecimal totalLiabilities, BigDecimal netWorth,
			BigDecimal monthlyIncome, BigDecimal monthlyExpense, BigDecimal monthlySavings, BigDecimal savingsRate,
			BigDecimal goalProgress, Integer monthsToExit) {
	}
}
