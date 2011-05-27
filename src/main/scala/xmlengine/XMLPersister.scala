package de.metacoder.blog.xmlengine

import de.metacoder.blog.util.Logging
import xml.XML

trait XMLPersister extends XMLLoader with Logging {

  protected def persist {
    logger debug "persisting authors and entries"

    val xml = <metacoder>
      <authors>
      {
        for((id, author) <- authors getOrElse loadAuthorsFromXML) yield {
          <author id={id.toString} name={author.name} email={author.email} password={author.password}>
            <description>{author.description}</description>
          </author>
        }
      }
      </authors>
      <entries>
        {
          for((entryId, entry) <- entries getOrElse loadEntriesFromXML) yield {
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
}