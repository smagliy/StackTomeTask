package com.crawler.config

object XpathConfig {
  val xpathForCategories: String = "//div[contains(@class, 'categoriesCarousel_carousel" +
    "Wrapper__VKRrz styles_carouselDesktop__SYTLm')]//div[contains(@class, 'paper_')]"
  val xpathForCompanies: String = "//div[@class='paper_paper__1PY90 paper_outline__" +
    "lwsUX card_card__lQWDv card_noPadding__D8PcU styles_wrapper__2JOo2']"
  val extraXpathForCompanies: String = "//div[@class='styles_businessUnitMain__PuwB7']"

  val xpathForTrafiic: String = "//div[@class='time-dot']//span[@class='time-dot-sum']"
}
