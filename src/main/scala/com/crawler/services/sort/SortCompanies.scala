package com.crawler.services.sort

import com.crawler.models._
import com.crawler.services.log.LogService

object SortCompanies {
  def sortCompaniesByReviewsAndTraffic(companies: List[Companies]): List[Companies] = {
    LogService.logger.info("Sorting the list by reviews and traffic parameters")
    companies.sortBy { company =>
      (-company.latestReviews.latestReviewCount, -company.monthlyVisits)
    }
  }
}
