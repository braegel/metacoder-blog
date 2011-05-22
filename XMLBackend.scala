package de.metacoder.blog.xmlengine 

import scala.actors.Actor
import scala.actors.Actor._
import de.metacoder.blog.entities._

object Persister extends Actor with StorageFileHandler {

	def act {
		loop {
			react {
				case ('persist, comments : List[Comment]) => println("persist called")
				case 'load => reply(List())
				case 'die => exit
				case _ => {
					import scala.xml.XML
					val xml = XML.loadFile(getOrCreateStorageFile)
					println(xml)
//					println("oh, my storage file is " + getOrCreateStorageFile)
					reply("schinken")
				}
			}
		}
	}
}

