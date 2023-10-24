package com.crawler.services.update

import com.crawler.models._
import com.crawler.services.parse.trustpilot.LatestReviewsParser
import com.crawler.services.parse.vstat.TrafficParser
import com.crawler.services.log.LogService

import scala.util.Try

class CompanyUpdater  {
  def update(company: Companies): Companies ={
    LogService.logger.info(s"Updating ${company.domain}")
    val updatedLatestReviews = updateLatestReviews(company.latestReviews, company.id)
    val updatedTraffic = new TrafficParser(List(company.domain)).parse().head
    company.update(updatedLatestReviews, updatedTraffic)
  }

  private def updateLatestReviews(oldLatestReviews: LatestReviews, id: String): LatestReviews = {
    LogService.logger.info("Trying to update latest reviews")
    val updaterInfo = Try(new LatestReviewsParser(List(id)).parse().head)
    oldLatestReviews.update(updaterInfo.getOrElse(oldLatestReviews).latestReview,
      updaterInfo.getOrElse(oldLatestReviews).listOfCommenterIDs)
  }
}
