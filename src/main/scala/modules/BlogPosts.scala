package de.metacoder.blog.modules

import de.metacoder.blog.entities.{Entry, Author}
import de.metacoder.blog.xmlengine.Persister
import collection.parallel.immutable.ParMap
import org.apache.log4j.Category._
import xml.Node


class BlogPosts extends Renderable {

  val matchingRule = IdMatchingRule("mainContentModule")

  var authors : Map[Long, Author] = null;
  var entries : Map[Long, Entry] = null;

  Persister !? 'load match {
    case (authors : Map[Long, Author], entries : Map[Long, Entry]) => {
      logger debug "received authors and entries from Persister"
      this.authors = authors;
      this.entries = entries;
    }
  }


  onRender {
    case _ => {
      <div id="blogEntries">
        {
          for ((key, value) <- entries) yield {
            <h3>{value.title}</h3>
            <div class="entry">
              {value.content}
            </div>
          }
        }
      </div>
    }
 }
}