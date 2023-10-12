package com.crawler.services.xpath

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.jdk.CollectionConverters._

import com.crawler.services.log.LogService

class XPathService {
  def getElementsFromXpath(XPath: String, doc: Document): Elements = {
    LogService.logger.info("Using an X-Path to extract part of an entity from the document")
    val elements = doc.selectXpath(XPath)
    elements
  }

  def getAttributeFromElements(elements: Elements, cssSelector: String, attribute: String): List[String] ={
    LogService.logger.info("Using a CSS selector to retrieve attribute values from an element")
    elements.select(cssSelector).eachAttr(attribute).asScala.toList
  }

  def getTextFromElements(elements: Elements): List[String] ={
    LogService.logger.info("Extracting the text from the HTML element")
    elements.eachText().asScala.toList
  }
}
