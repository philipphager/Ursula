package de.philipphager.ursula.model

class Page(val url: String,
           val html: String,
           val referencedUrls: List<String>)
