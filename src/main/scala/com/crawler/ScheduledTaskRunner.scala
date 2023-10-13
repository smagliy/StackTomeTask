package com.crawler

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import com.crawler.services.CrawlerPipeline
import com.crawler.services.log.LogService


object ScheduledTaskRunner {
  def main(args: Array[String]): Unit = {
    val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val task: Runnable = new Runnable {
      def run(): Unit = {
        CrawlerPipeline.sortedListOfCompanies
        LogService.logger.info("The updates for all files will be completed within 5 minutes...")
      }
    }
    val initialDelay = 0
    val period = 5
    scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MINUTES)

    sys.addShutdownHook {
      scheduler.shutdown()
    }
  }
}

