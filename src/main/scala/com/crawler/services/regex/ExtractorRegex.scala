package com.crawler.services.regex

import scala.util.matching.Regex

class ExtractorRegex {
  def extract(regex: Regex, input: String): Option[String] ={
    regex.findFirstMatchIn(input).map(_.group(1))
  }
}
