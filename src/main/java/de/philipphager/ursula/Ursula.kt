package de.philipphager.ursula

import de.philipphager.ursula.crawler.WebCrawler
import de.philipphager.ursula.domain.PageLoader
import de.philipphager.ursula.model.Page
import de.philipphager.ursula.network.HtmlParser
import de.philipphager.ursula.network.HttpClient
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.OkHttpClient
import java.util.regex.Pattern

class Ursula {

    companion object {
        @JvmStatic fun crawl(url: String,
                             shouldVisit: (url: String) -> Boolean,
                             visit: (Page) -> Unit) {
            val httpClient = HttpClient(OkHttpClient())
            val htmlParser = HtmlParser()
            val pageLoader = PageLoader(httpClient, htmlParser)
            val crawler = WebCrawler(pageLoader)

            runBlocking {
                crawler.crawl(url, shouldVisit, visit)
            }
        }

        @JvmStatic fun main(vararg args: String) {
            if (args.size != 1) throw IllegalArgumentException("Please provide a single URL as parameter to start crawling from")

            val startingUrl = args[0]
            val contentFilter = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$")
            crawl(startingUrl, { url ->
                !contentFilter.matcher(url).matches() && url.startsWith(startingUrl)
            }, { page ->
                println("Visited page: " + page.url)
                println("Referenced pages: " + page.referencedUrls)
                println("Thread: " + Thread.currentThread())
            })
        }
    }
}
