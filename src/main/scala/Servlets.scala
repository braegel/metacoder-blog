package de.metacoder.blog.servlets

import _root_.de.metacoder.blog.entities.{Entry, Author}
import _root_.de.metacoder.blog.util.Logging
import _root_.de.metacoder.blog.xmlengine.Persister
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import java.lang.RuntimeException
import collection.parallel.immutable.ParMap
import xml.XML
import java.util.Date
import xml.parsing.XhtmlParser

class MetacoderServlet extends HttpServlet with Logging {

  Persister start

  var authors : ParMap[Long, Author] = null;
  var entries : ParMap[Long, Entry] = null;

  Persister !? 'load match {
    case (authors : Map[Long, Author], entries : Map[Long, Entry]) => {
      logger debug "received authors and entries from Persister"
      this.authors = authors.par;
      this.entries = entries.par;
    }
  }

  override def doGet(request : HttpServletRequest, response : HttpServletResponse) : Unit = {
    response.getWriter.flush()
    logger debug ("loading xml" + new Date())
    val xhtml = new XhtmlParser(scala.io.Source.fromURL(getServletContext.getResource("/templates/blog.xhtml")))
    //val tmpl = XML load getServletContext.getResource("/templates/blog.xhtml")
    logger debug ("loaded xml" + new Date())
    response.getWriter.println((xhtml \ "title").text)
    response.getWriter.flush()
  }

}
//scala.xml.parsing.XhtmlParser