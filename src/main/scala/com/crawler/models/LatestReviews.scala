package com.crawler.models

case class LatestReviews(latestReview: Map[String, Any], listOfCommenterIDs: List[String], newLatestReviewCount: Int = 0) extends Base {
  override def toString: String = super.toString

  def update(newLatestReview: Map[String, Any], newListOfCommenterIDs: List[String]): LatestReviews = {
    val newListOfIDs = (listOfCommenterIDs ++ newListOfCommenterIDs).distinct
    val countNews = newLatestReviewCount + (newListOfIDs.size - listOfCommenterIDs.size)
    LatestReviews(newLatestReview, newListOfIDs, countNews)
  }
}
