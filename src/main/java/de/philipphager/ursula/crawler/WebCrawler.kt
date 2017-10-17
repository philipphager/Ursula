package de.philipphager.ursula.crawler

import de.philipphager.ursula.domain.PageLoader
import de.philipphager.ursula.model.Page
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.*

class WebCrawler(private val pageLoader: PageLoader) {
    private val visitedUrls = HashSet<String>()

    suspend fun crawl(url: String,
                      shouldVisit: (url: String) -> Boolean,
                      visit: (Page) -> Unit) {
        val page = pageLoader.load(url)
        visit(page)
        visitedUrls.add(url)

        page.referencedUrls
                .filter { !visitedUrls.contains(it) && shouldVisit(it) }
                .map { async(CommonPool) { crawl(it, shouldVisit, visit) } }
                .map { it.await() }
    }

    fun getVisitedUrls(): Set<String> = visitedUrls
}
