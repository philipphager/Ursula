package de.philipphager.ursula

import de.philipphager.ursula.crawler.WebCrawler
import de.philipphager.ursula.domain.PageLoader
import de.philipphager.ursula.model.Page
import de.philipphager.ursula.network.HtmlParser
import de.philipphager.ursula.network.HttpClient
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.OkHttpClient
import java.util.regex.Pattern

fun main(args: Array<String>) {
    if (args.size != 1) throw IllegalArgumentException("Please provide a single URL as parameter to start crawling from")

    val startingUrl = args[0]
    val httpClient = HttpClient(OkHttpClient())
    val htmlParser = HtmlParser()
    val pageLoader = PageLoader(httpClient, htmlParser)
    val crawler = object : WebCrawler(pageLoader) {
        private val CONTENT_FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$")

        override fun shouldVisit(url: String): Boolean {
            return !CONTENT_FILTERS.matcher(url).matches() && url.startsWith(startingUrl)
        }

        override fun visit(page: Page) {
            println("Visited page: " + page.url)
            println("Referenced pages: " + page.referencedUrls)
            println("Thread: " + Thread.currentThread())
            println("Total visited pages: " + getVisitedUrls().size)
        }
    }

    runBlocking {
        crawler.crawl(startingUrl)
    }
}
