package de.philipphager.ursula.network

class HtmlParser {
    companion object {
        /**
         * Reference @see [Stack Overflow - Regular Expressions](https://stackoverflow.com/questions/163360/regular-expression-to-match-urls-in-java)
         */
        val URL_PATTERN = java.util.regex.Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")!!
    }

    fun findReferences(html: String): List<String> {
        val urls = ArrayList<String>()
        val matcher = URL_PATTERN.matcher(html)

        while (matcher.find()) {
            urls.add(matcher.group())
        }
        return urls
    }
}
