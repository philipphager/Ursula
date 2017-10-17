# Ursula
A simple kotlin-based library to crawl web pages

## Usage
You can release the kraken from the command line by providing a single starting URL as argument or by using this project as a library:

```
Ursula.crawl("https://github.com", { url ->
    // Determine if an url should be crawled
    url.startsWith("https://github.com")
}, { page ->
    // Process the visited page
    println("Visited page: " + page.url)
})
```
