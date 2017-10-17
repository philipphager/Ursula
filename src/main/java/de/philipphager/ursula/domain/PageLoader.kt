package de.philipphager.ursula.domain

import de.philipphager.ursula.model.Page
import de.philipphager.ursula.network.HtmlParser
import de.philipphager.ursula.network.HttpClient

class PageLoader(private val httpClient: HttpClient,
                 private val htmlParser: HtmlParser) {

    fun load(url: String): Page {
        val html = httpClient.get(url)
        val references = htmlParser.findReferences(html)
        return Page(url, html, references)
    }
}
