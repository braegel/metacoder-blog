package de.metacoder.blog.xmlengine 

import java.io.File
import scala.xml.XML

trait StorageFileHandler {
	var storageFile : Option[File] = None

	def getOrCreateStorageFile = storageFile.getOrElse(createStorageFile)

	private def createStorageFile : File = {
		println("createStorageFile called")
		/* constants */
		val storageFileName = "storage.xml"
		val userHome = System.getProperty("user.home")
		val fileSeparator = System.getProperty("file.separator")
		val configDirName = ".metacoder"
		
		val configDir = new File(userHome + fileSeparator + configDirName)

		if(!configDir.isDirectory) {
			configDir.mkdirs
			// TODO log
		}


		val storageFile = new File(userHome + fileSeparator + configDirName + fileSeparator + storageFileName)

		if(!storageFile.exists){
			//storageFile.createNewFile
			val initXML = <metacoder />
			XML.save(storageFile.getAbsolutePath, initXML, "utf-8", true)
			// TODO log
		}


		this.storageFile = Some(storageFile)
		storageFile
	}
}

