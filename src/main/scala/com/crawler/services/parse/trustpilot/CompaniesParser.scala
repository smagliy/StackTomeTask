package com.crawler.services.parse.trustpilot

import org.jsoup.select.Elements

import com.crawler.config._
import com.crawler.models._
import com.crawler.services.log.LogService
import com.crawler.services.parse.vstat.TrafficParser
import com.crawler.services.regex.ExtractorRegex
import com.crawler.services.requests.HttpService
import com.crawler.services.xpath.XPathService


class CompaniesParser(mainXPath: String, categories: Categories)  {
  private val documentPage = new HttpService().getResponse(categories.href + CrawlerConfig.sortByCompanies).get
  private val serviceXpath = new XPathService()
  private val extractor = new ExtractorRegex()

  def parse(extraXpath: Option[String] = None): List[Companies] = {
    LogService.logger.info(s"Initiating the process of parsing the categories page (${categories.name})")
    val elements = serviceXpath.getElementsFromXpath(mainXPath, documentPage)
    val domains = matchDomain(elements)
    val traffic = new TrafficParser(domains).parse()
    val IDs = matchIDs(elements)
    val latestReviews = new LatestReviewsParser(IDs).parse()
    val listInfoAboutCompanies = matchInfoAboutCompany(extraXpath.getOrElse(""))
    println(categories.href + CrawlerConfig.sortByCompanies)
    listInfoAboutCompanies.zip(domains).zip(IDs).zip(latestReviews).zip(traffic).map {
      case ((((aboutCompanies, domainName), id), latestReviews), traffic) =>
        Companies(aboutCompanies.head.toInt, id, aboutCompanies(1),
          if (aboutCompanies(2).nonEmpty) aboutCompanies(2).toDouble else 0.0,
          domainName, aboutCompanies(3), categories, latestReviews, traffic)
    }
  }

  private def matchDomain(elements: Elements): List[String] ={
    LogService.logger.info("Searching for domains on the web")
    val domains: List[Option[String]] = serviceXpath.getAttributeFromElements(elements,
        "a[href]", "href").take(CrawlerConfig.limitRecords)
      .map { input => extractor.extract(RegexConfig.domainPattern, input) }
    domains.flatten
  }

  private def matchIDs(elements: Elements): List[String] ={
    LogService.logger.info("Searching for IDs on the web")
    val extractedIDs: List[Option[String]] = serviceXpath.getAttributeFromElements(elements,
        "source[srcset]", "srcset").take(CrawlerConfig.limitRecords)
      .map { input => extractor.extract(RegexConfig.idPattern, input) }
    extractedIDs.flatten
  }

  private def matchInfoAboutCompany(extraXpath: String): List[List[String]] = {
    LogService.logger.info("Getting general info (priority, ranks, name, reviews) about company from webpage")
    val textElements = serviceXpath.getTextFromElements(
      serviceXpath.getElementsFromXpath(mainXPath + extraXpath, documentPage)).take(CrawlerConfig.limitRecords)
    val extractedData: List[List[String]] = textElements.map { i =>
      val priority = (textElements.indexOf(i) + 1).toString
      val companyName = RegexConfig.companyNamePattern.findFirstMatchIn(i).map(_.group(1)).getOrElse("")
      val trustScore = RegexConfig.trustScorePattern.findFirstMatchIn(i).map(_.group(1)).getOrElse("")
      val reviewsCount = RegexConfig.reviewsPattern.findFirstMatchIn(i).map(_.group(1)).getOrElse("")
      List(priority, companyName, trustScore, reviewsCount)
    }
    extractedData
  }
}
