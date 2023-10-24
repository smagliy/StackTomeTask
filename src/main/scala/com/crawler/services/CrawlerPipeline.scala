package com.crawler.services

import com.crawler.config._
import com.crawler.models._
import com.crawler.services.log.LogService
import com.crawler.services.parse.trustpilot._
import com.crawler.services.sort.SortCompanies
import com.crawler.services.write.CSVWriter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object CrawlerPipeline {
  private def generateFutureListOfTasks(listCategories: List[Categories]): Future[List[List[Companies]]] ={
    val listFuturesResult: List[Future[List[Companies]]] = listCategories.map { category =>
      Future {
        val result = new CompaniesParser(XpathConfig.xpathForCompanies, category)
          .parse(Option(XpathConfig.extraXpathForCompanies))
        result
      }
    }
    Future.sequence(listFuturesResult)
  }

  def parseGeneralListOfCompanies: List[Companies] = {
    LogService.logger.info("Starting parse all of the pages that we're using")

    val listCategories = new CategoriesParser(CrawlerConfig.urlCategories, XpathConfig.xpathForCategories).parse()
    val listFutureOfLists: Future[List[List[Companies]]] = generateFutureListOfTasks(listCategories)
    val listCompaniesFuture: Future[List[Companies]] = listFutureOfLists.map(_.flatten)

    Await.result(listCompaniesFuture, Duration.Inf)
  }

  def sortAndWriteListOfCompanies(listOfCompanies: List[Companies]): Unit = {
    val sortedInfo = SortCompanies.sortCompaniesByReviewsAndTraffic(listOfCompanies)
    CSVWriter.writeCompaniesToCsv(sortedInfo, CrawlerConfig.pathToSortInfoAboutCompaniesGeneral)
  }
}
