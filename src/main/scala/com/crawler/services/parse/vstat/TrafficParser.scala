package com.crawler.services.parse.vstat

import com.crawler.config._
import com.crawler.services.requests.HttpService
import com.crawler.services.xpath.XPathService
import com.crawler.services.log.LogService

import org.jsoup.nodes.Document

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}


class TrafficParser(listDomains: List[String]) {
  import scala.concurrent.ExecutionContext.Implicits.global
  private val httpService = new HttpService()
  private val serviceXpath = new XPathService()
  private var trafficResults: List[Int] = List()

  def parse(): List[Int] = {
    LogService.logger.info("Getting web traffic from specific domains")
    val futureResult = for (domain <- listDomains)
      yield Future {
        lookForTraffic(httpService.getResponse(CrawlerConfig.urlTraffic + domain).get)
      }
    futureResult.foreach { item =>
      val result = Try(Await.result(item, Duration.Inf))
      result match {
        case Success(traffic) => trafficResults ++= List(traffic)
        case Failure(exception) =>
          LogService.logger.debug(s"An exception is being encountered while parsing the " +
            s"traffic domain. Exception: ${exception}")
      }
    }
    trafficResults
  }

  def lookForTraffic(document: Document): Int = {
    try {
      LogService.logger.info("Trying to get monthly visits from site")
      val elements = serviceXpath.getElementsFromXpath(XpathConfig.xpathForTrafiic,document)
      val monthlyVisits = serviceXpath.getAttributeFromElements(elements,
        "span", "data-datum").head.toInt
      monthlyVisits
    } catch {
      case e: Exception =>
        LogService.logger.info("Information about monthly visits aren't have in the site")
        0
    }
  }
}