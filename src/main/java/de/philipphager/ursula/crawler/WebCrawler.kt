package de.philipphager.ursula.crawler

import de.philipphager.ursula.domain.PageLoader
import de.philipphager.ursula.model.Page
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.*

abstract class WebCrawler(private val pageLoader: PageLoader) {
    private val visitedUrls = HashSet<String>()

    suspend fun crawl(url: String) {
        val page = pageLoader.load(url)
        visit(page)
        visitedUrls.add(url)

        page.referencedUrls
                .filter { !visitedUrls.contains(it) && shouldVisit(it) }
                .map { async(CommonPool) { crawl(it) } }
                .map { it.await() }
    }

    internal abstract fun shouldVisit(url: String): Boolean

    internal abstract fun visit(page: Page)

    fun getVisitedUrls(): Set<String> = visitedUrls
}
