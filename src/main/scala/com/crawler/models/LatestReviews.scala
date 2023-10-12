package com.crawler.models

case class LatestReviews(latestReview: Map[String, Any], latestReviewCount: Int) extends Base {
  override def toString: String = super.toString
}
