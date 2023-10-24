package com.crawler

import scala.concurrent.duration._
import com.crawler.models._
import com.crawler.services.CrawlerPipeline
import com.crawler.services.update.CompanyUpdater
import com.crawler.services.log.LogService

object Main {
  import scala.concurrent.ExecutionContext.Implicits.global

  private val crawler = CrawlerPipeline
  private val modelUpdater = new CompanyUpdater

  def main(args: Array[String]): Unit = {
    val initialCompanies: List[Companies] = crawler.parseGeneralListOfCompanies
    crawler.sortAndWriteListOfCompanies(initialCompanies)
    while (true) {
      LogService.logger.info("Waiting 5 min for updating info")
      Thread.sleep(5.minutes.toMillis)
      val updatedCompanies = initialCompanies.map(company => modelUpdater.update(company))
      crawler.sortAndWriteListOfCompanies(updatedCompanies)
    }
  }
}
