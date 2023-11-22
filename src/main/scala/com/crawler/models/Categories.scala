package com.crawler.models

case class Categories(name: String, href: String) extends Base {
  override def toString: String = super.toString

  override def updateLatestReview(updaterLatestReviews: LatestReviews): Base = ???
}
