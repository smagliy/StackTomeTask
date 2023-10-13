package com.crawler

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import com.crawler.services.CrawlerPipeline


object ScheduledTaskRunner {
  def main(args: Array[String]): Unit = {
    val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val task: Runnable = new Runnable {
      def run(): Unit = {
        CrawlerPipeline.sortedListOfCompanies
        println("Task is running...")
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

