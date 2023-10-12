package com.crawler.services.requests

import org.jsoup._
import org.jsoup.nodes.Document

import scala.io.Source

import com.crawler.services.log.LogService

class HttpService {
  def getResponse(url: String): Option[Document] = {
    try {
      LogService.logger.debug("Trying to get a document of webpage")
      val doc = Jsoup.connect(url).get()
      Some(doc)
    } catch {
      case e: Exception =>
        LogService.logger.debug(s"Error fetching URL: $url - ${e.getMessage}")
        None
    }
  }

  def getTextFromUrl(url: String): String = {
    LogService.logger.debug("Collecting information from the webpage")
    val source = Source.fromURL(url)
    val response = source.mkString
    source.close()
    response
  }
}
