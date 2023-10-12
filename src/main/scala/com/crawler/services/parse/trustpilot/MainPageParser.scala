package com.crawler.services.parse.trustpilot

import com.crawler.models.Categories
import com.crawler.services.requests.HttpService
import com.crawler.services.xpath.XPathService
import org.jsoup.select.Elements
import com.crawler.config.CrawlerConfig
import com.crawler.services.log.LogService


class MainPageParser(url: String, XPath: String) extends BaseParser {
  private val documentPage = new HttpService().getResponse(url).get
  private val serviceXpath = new XPathService()

  def parse(extraXpath: Option[String] = None): List[Categories] ={
    LogService.logger.info("Parsing the main page for getting all categories " +
      "and their parts of urls")
    val elements: Elements = serviceXpath.getElementsFromXpath(XPath, documentPage)
    serviceXpath.getAttributeFromElements(elements,
      "a[href]", "href").zip(serviceXpath.getTextFromElements(elements
    )).map{
      case (href, name) => Categories(name, CrawlerConfig.urlCategories + href)
    }
  }
}
