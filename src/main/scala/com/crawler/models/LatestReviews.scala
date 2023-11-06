package com.crawler.models

case class LatestReviews(latestReview: Map[String, Any], listOfCommenterIDs: List[String], newLatestReviewCount: Int = 0) extends Base {
  override def toString: String = super.toString

  def updateLatestReview(updaterLatestReviews: LatestReviews): LatestReviews = {
    val newListOfIDs = (listOfCommenterIDs ++ updaterLatestReviews.listOfCommenterIDs).distinct
    val countNews = newLatestReviewCount + (newListOfIDs.size - listOfCommenterIDs.size)
    LatestReviews(updaterLatestReviews.latestReview, newListOfIDs, countNews)
  }
}
