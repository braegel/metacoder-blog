package de.metacoder.blog.xmlengine

import de.metacoder.blog.util.Logging
import de.metacoder.blog.entities._
import scala.actors.Actor

object Persister extends Actor with XMLPersister with XMLLoader with Logging {

  start // run actor when object initializes

	def act {
		loop {
			react {

				case 'load => {
          reply((authors getOrElse loadAuthorsFromXML,entries getOrElse loadEntriesFromXML))
        }

        case ('persist, authors : Map[Long, Author], entries : Map[Long, Entry]) => {
          this.authors = Some(authors)
          this.entries = Some(entries)
          persist
        }

        case ('persist, authors : Map[Long, Author]) => {
          this.authors = Some(authors)
          persist
        }

        case ('persist, entries : Map[Long, Entry]) => {
          this.entries = Some(entries)
          persist
        }

        case ('persist) => persist
				case 'die => exit
				case x => logger fatal ("illegal call in Persister Actor " + x)

			}
		}
  }
}

