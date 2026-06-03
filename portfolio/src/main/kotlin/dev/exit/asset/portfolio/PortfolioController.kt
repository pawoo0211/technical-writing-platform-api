package dev.exit.asset.portfolio

import dev.exit.asset.common.CurrentUserProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/api/v1/portfolio")
class PortfolioController(
    private val currentUser: CurrentUserProvider,
    private val summaries: PortfolioSummaryService,
) {

    @GetMapping("/summary")
    fun summary(@RequestParam(required = false) month: YearMonth?): PortfolioSummaryService.PortfolioSummary =
        summaries.summarize(currentUser.ownerId(), month ?: YearMonth.now())
}
