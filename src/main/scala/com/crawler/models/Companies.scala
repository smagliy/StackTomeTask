package com.crawler.models

case class Companies(priority: Int, id: String, name: String,
                     ranks: Double, domain: String, reviews: String,
                     categories: Categories, latestReviews: LatestReviews, monthlyVisits: Int = 0) extends Base {
  override def toString: String = super.toString

  def updateLatestReview(updaterLatestReviews: LatestReviews): Companies={
    Companies(priority, id, name, ranks, domain, reviews, categories,
      updaterLatestReviews, monthlyVisits)
  }

  def updateCategories(category: Categories): Companies = {
    Companies(priority, id, name, ranks, domain, reviews, category, latestReviews, monthlyVisits)
  }
}
