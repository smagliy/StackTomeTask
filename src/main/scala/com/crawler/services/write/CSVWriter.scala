package com.crawler.services.write

import java.io.FileWriter

import org.apache.commons.csv.{CSVFormat, CSVPrinter}

import org.json4s._
import org.json4s.jackson.Serialization.write

import com.crawler.models._
import com.crawler.services.log.LogService
import com.crawler.services.write.utils.FilesService

object CSVWriter {
  implicit val formats: Formats = DefaultFormats
  private val csvFormat = CSVFormat.DEFAULT.withHeader("priority","id","name","ranks",
    "domain","reviews","categories","latest_review","latest_reviews_count","monthly_visits")

  def writeCompaniesToCsv(companies: List[Companies], filePath: String): Unit = {
    LogService.logger.info(s"Trying to write the results of list companies in the ${filePath}")
    FilesService.createOrClearFile(filePath)
    val fileWriter = new FileWriter(filePath)
    val csvPrinter = new CSVPrinter(fileWriter, csvFormat)

    try {
      companies.foreach { company =>
        csvPrinter.printRecord(company.priority, company.id, company.name, company.ranks,
          company.domain, company.reviews, company.categories.name, write(company.latestReviews.latestReview),
          company.latestReviews.newLatestReviewCount, company.monthlyVisits)
      }
    } finally {
      fileWriter.close()
      csvPrinter.close()
    }
  }
}
