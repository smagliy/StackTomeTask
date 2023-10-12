package com.crawler.models

case class Companies(priority: Int, id: String, name: String,
                     ranks: Double, domain: String, reviews: String,
                     categories: Categories, latestReviews: LatestReviews, monthlyVisits: Int = 0) extends Base {
  override def toString: String = super.toString
}
