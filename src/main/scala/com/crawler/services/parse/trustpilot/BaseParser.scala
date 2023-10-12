package com.crawler.services.parse.trustpilot

import com.crawler.models.Base

trait BaseParser {
  def parse(extraXpath: Option[String] = None): List[Base]
}
