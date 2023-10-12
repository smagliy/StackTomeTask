package com.crawler.services.write.utils

import java.io.File
import com.crawler.services.log.LogService

object FilesService {
  def createOrClearFile(filePath: String): Unit = {
    LogService.logger.info(s"Creating or clearing the file in the ${filePath}")
    val file = new File(filePath)
    val directory = file.getParentFile
    if (!directory.exists()) {
      directory.mkdirs()
    }
    if (!file.exists()) {
      file.createNewFile()
    }
  }
}
