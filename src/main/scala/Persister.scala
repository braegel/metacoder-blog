package de.metacoder.blog.xmlengine

import _root_.de.metacoder.blog.util.Logging
import scala.actors.Actor
import scala.actors.Actor._
import de.metacoder.blog.entities._

object Persister extends Actor with StorageFileHandler with Logging {

	def act {
		loop {
			react {
				case ('persist, comments : List[Comment]) => println("persist called")
				case 'load => reply(loadEntriesFromXML)
				case 'die => exit
				case x => {
				  logger fatal ("illegal call in Persister Actor " + x)
				}
			}
		}
	}

  private def loadEntriesFromXML : List[Entry] = {
    import scala.xml.XML
		val xml = XML loadFile getOrCreateStorageFile
		logger debug xml
    List()
  }
}

