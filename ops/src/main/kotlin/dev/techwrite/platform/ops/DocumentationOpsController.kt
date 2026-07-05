package dev.techwrite.platform.ops

import dev.techwrite.platform.common.CurrentUserProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/api/v1/ops")
class DocumentationOpsController(
    private val currentUser: CurrentUserProvider,
    private val summaries: DocumentationOpsSummaryService,
) {

    @GetMapping("/summary")
    fun summary(@RequestParam(required = false) month: YearMonth?): DocumentationOpsSummaryService.DocumentationOpsSummary =
        summaries.summarize(currentUser.ownerId(), month ?: YearMonth.now())
}
