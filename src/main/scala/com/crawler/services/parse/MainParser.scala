package com.crawler.services.parse

import com.crawler.config._
import com.crawler.models._
import com.crawler.services.log.LogService
import com.crawler.services.parse.trustpilot._
import com.crawler.services.write.CSVWriteService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

object MainParser {
  import scala.concurrent.ExecutionContext.Implicits.global
  private var listCompanies: List[Companies] = List()

  def parse(): List[Companies] = {
    LogService.logger.info("Starting parse all of the pages that we using it")
    val listCategories = new MainPageParser(CrawlerConfig.urlCategories,
      XpathConfig.xpathForCategories).parse()
    val listFuturesResult = for (category <- listCategories)
      yield Future {
        new CategoriesParser(XpathConfig.xpathForCompanies,
          category).parse(Option(XpathConfig.extraXpathForCompanies))
      }
    listFuturesResult.foreach { item =>
      val result = Try(Await.result(item, Duration.Inf))
      result match {
        case Success(companiesCategoryList) =>
          val pathToFile = CrawlerConfig.pathToReportCompaniesFiles + companiesCategoryList.head.categories.name + ".csv"
          CSVWriteService.writeCompaniesToCsv(companiesCategoryList, pathToFile)
          listCompanies ++= companiesCategoryList
        case Failure(exception) => LogService.logger.debug(s"An exception is being encountered while " +
          s"parsing the all of categories. Exception: ${exception}")
      }
    }
    listCompanies
  }
}
