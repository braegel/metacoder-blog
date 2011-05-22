package de.metacoder.blog.xmlengine

import _root_.de.metacoder.blog.util.Logging
import scala.actors.Actor
import scala.actors.Actor._
import de.metacoder.blog.entities._
import xml.{Node, XML}
import java.util.Date


object Persister extends Actor with StorageFileHandler with Logging {

  var authors = Map[Long, Author]()
  var entries = Map[Long, Entry]()

	def act {
		loop {
			react {

				case 'load => {
          val xml = XML loadFile getOrCreateStorageFile
          loadAuthorsFromXML(xml)
          loadEntriesFromXML(xml)
          reply((authors,entries))
        }

        case ('persist, authors : Map[Long, Author], entries : Map[Long, Entry]) => {
          this.authors = authors
          this.entries = entries
          persist
        }

        case ('persist, authors : Map[Long, Author]) => {
          this.authors = authors
          persist
        }

        case ('persist, entries : Map[Long, Entry]) => {
          this.entries = entries
          persist
        }

				case 'die => exit
				case x => {
				  logger fatal ("illegal call in Persister Actor " + x)
				}
			}
		}
	}

  private def persist(){
    logger debug "persisting authors and entries"
    logger error "uhm, implement me?" // TODO
    logger debug "persisted authors and entries"
  }

  private def loadAuthorsFromXML(xml : Node) {
    logger debug "loading authors from xml"
    var newAuthors = Map[Long, Author]()
    for(authorElem <- xml \\ "author"){
      val id =          (authorElem \ "@id").text.toLong
      val name =        (authorElem \ "@name").text
      val email =       (authorElem \ "@email").text
      val realName =    (authorElem \ "@realname" ).text
      val description = (authorElem \ "description").text
      val password =    (authorElem \ "@password").text

      newAuthors = newAuthors + (id -> Author(id,name,email,realName,description, password))
    }

    authors = newAuthors
    logger debug "loaded authors from xml" + authors
  }

  private def loadEntriesFromXML(xml : Node) {
    logger debug "loading entries from xml"
    var newEntries = Map[Long, Entry]()

    for(entry <- xml \\ "entry"){

      val id = (entry \ "@id").text.toLong
      val date = new Date((entry \ "@date").text.toLong);
      val author = authors.get((entry \ "@author").text.toLong).get
      val title = (entry \ "title").text
      val content = (entry \ "content").text

      //for()


      newEntries = newEntries + ( id -> Entry(id, date, author, title, content, Map[Long, Comment]()))
    }

    entries = newEntries
    logger debug "loaded entries from xml: " + entries
  }
}

