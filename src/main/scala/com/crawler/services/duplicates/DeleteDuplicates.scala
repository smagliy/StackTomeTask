package com.crawler.services.duplicates

import com.crawler.models.{Categories, Companies}

object DeleteDuplicates {
  def deleteDuplicatesOfCompanies(companies: List[Companies]): List[Companies] = {
    companies.groupBy(_.id).map {
      case (_, companyList) =>
        val firstCompany = companyList.head
        val categories = Categories(companyList.map(_.categories.name).mkString(", "),
          companyList.map(_.categories.href).mkString(", "))
        firstCompany.updateCategories(categories)
    }.toList
  }

}
