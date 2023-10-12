package com.crawler

import com.crawler.services.parse.MainParser
import com.crawler.config.CrawlerConfig
import com.crawler.services.sort.SortCompanies
import com.crawler.services.write.CSVWriteService

object Main {
  def main(args: Array[String]): Unit = {
    val listOfCompanies = MainParser.parse()
    val sortedInfo = SortCompanies.sortCompaniesByReviewsAndTraffic(listOfCompanies)
    CSVWriteService.writeCompaniesToCsv(sortedInfo, CrawlerConfig.pathToSortInfoAboutCompaniesGeneral)
  }
}
