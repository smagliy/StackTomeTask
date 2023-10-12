package com.crawler.services.parse.trustpilot

import org.json4s._
import org.json4s.jackson.JsonMethods

import com.crawler.services.requests.HttpService
import com.crawler.models.LatestReviews
import com.crawler.config.CrawlerConfig
import com.crawler.services.log.LogService

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}
import scala.concurrent.{Await, Future}

class LatestReviewsParser(listIDs: List[String]) extends BaseParser {
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val formats: DefaultFormats.type = DefaultFormats
  private val httpService = new HttpService
  private var latestReviews: List[LatestReviews] = List()

  def parse(extraXpath: Option[String] = None): List[LatestReviews] = {
    LogService.logger.info("Accessing the latest reviews in json format from APIs")
    val futureResult = for (id <- listIDs)
      yield Future {
        parseJsonResponse(httpService.getTextFromUrl(
          CrawlerConfig.urlAPICategories + id + CrawlerConfig.sortByLatestReviews))
      }
    futureResult.foreach { item =>
      val result = Try(Await.result(item, Duration.Inf))
      result match {
        case Success(latestReviewList) =>
          latestReviews ++= latestReviewList
        case Failure(exception) =>
          LogService.logger.debug(s"An exception was encountered while parsing " +
            s"the latest review. Exception: ${exception}")
      }
    }
    latestReviews
  }

  private def parseJsonResponse(response: String): List[LatestReviews] ={
    LogService.logger.info("Parsing json requests from the API")
    val latestReviewsList = (JsonMethods.parse(response) \ "reviews").extract[List[Map[String, Any]]]
    List(LatestReviews(latestReviewsList.head, latestReviewsList.size))
  }
}