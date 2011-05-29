package de.metacoder.blog.servlets

import _root_.de.metacoder.blog.modules.{Greeting, Renderable}
import de.metacoder.blog.entities.{Entry, Author}
import de.metacoder.blog.util.Logging
import de.metacoder.blog.xmlengine.Persister
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import java.lang.RuntimeException
import collection.parallel.immutable.ParMap
import xml.XML
import java.util.Date
import xml.parsing.XhtmlParser
import collection.immutable.HashMap
import java.security.Key

class MetacoderServlet extends HttpServlet with Logging {

  Persister start

  var authors : ParMap[Long, Author] = null;
  var entries : ParMap[Long, Entry] = null;
  val modules = Map("greetingModule" -> new Greeting).par


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
    val xhtml = XhtmlParser(scala.io.Source.fromURL(getServletContext.getResource("/templates/blog.xhtml")))
    logger debug ("loaded xml" + new Date())
    response.getWriter.println(xhtml)
    for((key, value) <- modules){
      //xhtml match {
      //  case entry @id == key =>
      //}
      response.getWriter.println(value.render(request.getRequestURL.toString))
    }
    response.getWriter.flush()
  }
}
