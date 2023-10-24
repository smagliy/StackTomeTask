package com.crawler.config

object CrawlerConfig {
  val urlCategories: String = "https://www.trustpilot.com/"
  val urlAPICategories: String = "https://www.trustpilot.com/api/categoriespages/"
  val urlTraffic: String = "https://vstat.info/"

  val limitRecords: Int = 10

  val sortByCompanies = "?sort=latest_review"
  val sortByLatestReviews = "/reviews?locale=en-US"

  val pathToSortInfoAboutCompaniesGeneral = "./results/sort/sort_by_reviews_and_traffic.csv"
}

