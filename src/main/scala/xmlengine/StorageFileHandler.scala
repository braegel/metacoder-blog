package de.metacoder.blog.xmlengine

import de.metacoder.blog.util.Logging
import java.io.File
import scala.xml.XML

trait StorageFileHandler extends Logging {

	private var storageFile : Option[File] = None

	def getOrCreateStorageFile = storageFile.getOrElse(createStorageFile)

	private def createStorageFile : File = {
		logger debug "createStorageFile called"
		/* constants */
		val storageFileName = "storage.xml"
		val userHome = System.getProperty("user.home")
		val fileSeparator = System.getProperty("file.separator")
		val configDirName = ".metacoder"
		
		val configDir = new File(userHome + fileSeparator + configDirName)

		if(!configDir.isDirectory) {
			logger info "creating persistence folder in " + configDir.getAbsolutePath
      configDir.mkdirs
		}

		val storageFile = new File(userHome + fileSeparator + configDirName + fileSeparator + storageFileName)

		if(!storageFile.exists){
      logger info "creating persistent xml storage in " + storageFile.getAbsolutePath
			val initXML = <metacoder />
			XML.save(storageFile.getAbsolutePath, initXML, "utf-8", true)
		}

		this.storageFile = Some(storageFile)
		storageFile
	}
}

