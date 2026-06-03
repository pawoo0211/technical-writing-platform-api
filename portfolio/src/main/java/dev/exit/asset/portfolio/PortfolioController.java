package dev.exit.asset.portfolio;

import java.time.YearMonth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.exit.asset.common.CurrentUserProvider;
import dev.exit.asset.portfolio.PortfolioSummaryService;
import dev.exit.asset.portfolio.PortfolioSummaryService.PortfolioSummary;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

	private final CurrentUserProvider currentUser;

	private final PortfolioSummaryService summaries;

	public PortfolioController(CurrentUserProvider currentUser, PortfolioSummaryService summaries) {
		this.currentUser = currentUser;
		this.summaries = summaries;
	}

	@GetMapping("/summary")
	public PortfolioSummary summary(@RequestParam(required = false) YearMonth month) {
		return summaries.summarize(currentUser.ownerId(), month == null ? YearMonth.now() : month);
	}
}
