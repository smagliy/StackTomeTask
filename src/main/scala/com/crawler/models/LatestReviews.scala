package com.crawler.models

case class LatestReviews(latestReview: Map[String, Any], listOfCommenterIDs: List[String]) extends Base {
  override def toString: String = super.toString

  def update(newLatestReview: Map[String, Any], newListOfCommenterIDs: List[String]): LatestReviews = {
    LatestReviews(newLatestReview, (listOfCommenterIDs ++ newListOfCommenterIDs).distinct)
  }
}
