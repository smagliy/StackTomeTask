package com.crawler

import scala.concurrent.duration._
import com.crawler.models._
import com.crawler.services.CrawlerPipeline
import com.crawler.services.update.CompanyUpdater
import com.crawler.services.log.LogService

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main {
  import scala.concurrent.ExecutionContext.Implicits.global

  private val crawler = CrawlerPipeline
  private var companiesList: List[Companies] = crawler.parseGeneralListOfCompanies
  private val modelUpdater = new CompanyUpdater

  private def generateFutureList: Future[List[Companies]] = {
    val updateFutures: List[Future[Companies]] = companiesList.map { company =>
      Future { modelUpdater.update(company) }
    }
    Future.traverse(updateFutures)(identity)
  }

  def main(args: Array[String]): Unit = {
    crawler.sortAndWriteListOfCompanies(companiesList)
    while (true) {
      LogService.logger.info("Waiting 5 min for updating")
      Thread.sleep(5.minutes.toMillis)
      generateFutureList.onComplete {
        case Success(updatedCompanies) =>
          companiesList = updatedCompanies.distinct
          crawler.sortAndWriteListOfCompanies(companiesList)
        case Failure(exception) =>
          LogService.logger.error("Error during company updates: " + exception.getMessage)
      }
    }
  }
}
