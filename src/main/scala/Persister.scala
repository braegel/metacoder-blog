package de.metacoder.blog.xmlengine

import _root_.de.metacoder.blog.util.Logging
import scala.actors.Actor
import de.metacoder.blog.entities._
import xml.XML
import java.util.Date


object Persister extends Actor with StorageFileHandler with Logging {

  var authors : Option[Map[Long, Author]] = None
  var entries : Option[Map[Long, Entry]] = None

	def act {
		loop {
			react {

				case 'load => {
          loadAuthorsFromXML
          loadEntriesFromXML
          reply((authors,entries))
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

        case ('persist) => {
           persist
        }

				case 'die => exit
				case x => {
				  logger fatal ("illegal call in Persister Actor " + x)
				}
			}
		}
	}

  private def persist {
    logger debug "persisting authors and entries"

    val xml = <metacoder>
      <authors>
      {
        for((id, author) <- authors.getOrElse({loadAuthorsFromXML; authors.get})) yield {
          <author id={id.toString} name={author.name} email={author.email} password={author.password}>
            <description>{author.description}</description>
          </author>
        }
      }
      </authors>
      <entries>
        {
          for((entryId, entry) <- entries.getOrElse({loadEntriesFromXML; entries.get})) yield {
            <entry id={entryId.toString} date={entry.date.getTime.toString} authorId={entry.authorId.toString} authorName={entry.authorName}>
              <title>{entry.title}</title>
              <content>{entry.content}</content>
              <comments>
                {
                  for((commentId, comment) <- entry.comments) yield {
                    <comment id={commentId.toString} date={comment.date.getTime.toString} authorName={comment.authorName}>
                      <content>{comment.content}</content>
                    </comment>
                  }
                }
              </comments>
            </entry>
          }
        }
      </entries>
    </metacoder>

    XML.save(getOrCreateStorageFile.getAbsolutePath, xml, "UTF-8", true)
    logger debug "persisted authors and entries in " + getOrCreateStorageFile.getAbsolutePath
  }

  private def loadAuthorsFromXML {
    val xml = XML loadFile getOrCreateStorageFile
    logger debug "loading authors from xml"
    var newAuthors = Map[Long, Author]()
    for(authorElem <- xml \\ "author"){
      val id =          (authorElem \ "@id").text.toLong
      val name =        (authorElem \ "@name").text
      val email =       (authorElem \ "@email").text
      val description = (authorElem \ "description").text
      val password =    (authorElem \ "@password").text

      newAuthors = newAuthors + (id -> Author(id,name,email,description, password))
    }

    authors = Some(newAuthors)
    logger debug "loaded authors from xml"
    logger trace authors
  }

  private def loadEntriesFromXML {
    logger debug "loading entries from xml"
    val xml = XML loadFile getOrCreateStorageFile
    var newEntries = Map[Long, Entry]()

    for(entry <- xml \\ "entry"){

      val id = (entry \ "@id").text.toLong
      val date = new Date((entry \ "@date").text.toLong)
      val authorId = ( entry \ "@authorId").text.toLong
      val authorName = ( entry \ "@authorName").text
      val title = (entry \ "title").text
      val content = (entry \ "content").text

      var comments = Map[Long, Comment]()
      for(comment <- entry \ "comments" \\ "comment"){
        val commentId = (comment \ "@id").text.toLong
        val commentDate = new Date((comment \ "@date").text.toLong)
        val commentAuthor = (comment \ "@authorName").text
        val commentContent = (comment \ "content").text

        comments = comments + ( commentId -> Comment(commentId, commentDate, commentAuthor, commentContent))
      }

      newEntries = newEntries + ( id -> Entry(id, date, authorId, authorName, title, content, comments))
    }

    entries = Some(newEntries)
    logger debug "loaded entries from xml"
    logger trace entries
  }
}

