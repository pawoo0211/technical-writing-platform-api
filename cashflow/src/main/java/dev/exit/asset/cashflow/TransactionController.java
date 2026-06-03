package dev.exit.asset.cashflow;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.exit.asset.common.CurrentUserProvider;
import dev.exit.asset.cashflow.CashFlowTransaction;
import dev.exit.asset.cashflow.TransactionType;
import dev.exit.asset.cashflow.CashFlowTransactionRepository;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	private final CurrentUserProvider currentUser;

	private final CashFlowTransactionRepository transactions;

	public TransactionController(CurrentUserProvider currentUser, CashFlowTransactionRepository transactions) {
		this.currentUser = currentUser;
		this.transactions = transactions;
	}

	@GetMapping
	public List<CashFlowTransaction> list() {
		return transactions.findByOwnerIdOrderByTransactedOnDesc(currentUser.ownerId());
	}

	@PostMapping
	public CashFlowTransaction create(@Valid @RequestBody CreateTransactionRequest request) {
		return transactions.save(new CashFlowTransaction(currentUser.ownerId(), request.transactedOn(), request.type(),
				request.category(), request.memo(), request.amount()));
	}

	public record CreateTransactionRequest(@NotNull LocalDate transactedOn, @NotNull TransactionType type,
			@NotBlank String category, @NotBlank String memo, @NotNull @Positive BigDecimal amount) {
	}
}
