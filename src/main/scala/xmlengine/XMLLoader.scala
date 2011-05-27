package de.metacoder.blog.xmlengine

import de.metacoder.blog.util.Logging
import de.metacoder.blog.entities.{Comment, Entry, Author}
import xml.XML
import java.util.Date

trait XMLLoader extends StorageFileHandler with Logging {

  var authors : Option[Map[Long, Author]] = None
  var entries : Option[Map[Long, Entry]] = None

  protected def loadAuthorsFromXML = {
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
    newAuthors
  }

  protected def loadEntriesFromXML = {
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
    newEntries
  }
}