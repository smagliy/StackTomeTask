package com.crawler.config

import scala.util.matching.Regex

object RegexConfig {
  val domainPattern: Regex = "/review/(.+)".r
  val idPattern: Regex = "business-units/([0-9a-fA-F]+)-\\d+x\\d+-\\d+x\\.(?:avif|jpg)".r
  val companyNamePattern: Regex = """^(.*?)\s+TrustScore""".r
  val trustScorePattern: Regex = """TrustScore\s+(\d+\.\d+)""".r
  val reviewsPattern: Regex = """\|(\d{1,3}(?:,\d{3})*)(?:\s+reviews|$)""".r
}
