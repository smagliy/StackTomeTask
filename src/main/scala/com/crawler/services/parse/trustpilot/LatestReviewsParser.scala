package com.crawler.services.parse.trustpilot

import org.json4s._
import org.json4s.jackson.JsonMethods
import com.crawler.services.requests.HttpService
import com.crawler.models.LatestReviews
import com.crawler.config.CrawlerConfig
import com.crawler.services.log.LogService

import scala.util.Try


class LatestReviewsParser(listIDs: List[String]) extends BaseParser {
  implicit val formats: DefaultFormats.type = DefaultFormats
  private val httpService = new HttpService

  def parse(extraXpath: Option[String] = None): List[LatestReviews] = {
    LogService.logger.info("Accessing the latest reviews in json format from APIs")
    val resultOfParse = listIDs.map(id =>
      parseJsonResponse(httpService.getTextFromUrl(
        CrawlerConfig.urlAPICategories + id + CrawlerConfig.sortByLatestReviews))
    )
    resultOfParse
  }

  private def parseJsonResponse(response: String): LatestReviews ={
    LogService.logger.info("Parsing json requests from the API")
    val latestReviewsList = (JsonMethods.parse(response) \ "reviews").extract[List[Map[String, Any]]]
    val idList: List[String] = latestReviewsList.flatMap { map =>
      map.get("id") match {
        case Some(id: String) => List(id)
        case _ => List()
      }}
    LatestReviews(latestReviewsList.head, idList)
  }
}