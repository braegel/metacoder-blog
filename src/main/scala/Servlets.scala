package de.metacoder.blog.servlets

import _root_.de.metacoder.blog.modules.{BlogPosts, Copyright, Renderable}
import de.metacoder.blog.entities.{Entry, Author}
import de.metacoder.blog.util.Logging
import de.metacoder.blog.xmlengine.Persister
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import collection.parallel.immutable.ParMap
import xml.parsing.XhtmlParser
import xml.transform.{RuleTransformer, RewriteRule}
import xml._


class MetacoderServlet extends HttpServlet with Logging {



  val modules : Map[String, Renderable] = Map("copyrightModule" -> new Copyright, "blogpostsModule" -> new BlogPosts)



  override def doGet(request : HttpServletRequest, response : HttpServletResponse) : Unit = {
    logger debug "loading xhtml template"
    var xhtmlTemplate = XhtmlParser(scala.io.Source.fromURL(getServletContext.getResource("/templates/blog.xhtml")))
    for((key, value) <- modules){
      logger debug  "trying to apply module " + key

      val moduleRenderOutput = value.render(request.getRequestURL.toString)

      object ModuleInjectRule extends RewriteRule {
        override def transform(n: Seq[Node]): Seq[Node] = n match {
          case Elem(prefix, tag, attribs, scope, _*) if attribs.get("id").exists(_.text == key) => {
            Elem(prefix, tag, attribs, scope, moduleRenderOutput)
          }
          case other => other
        }
      }

      xhtmlTemplate = new RuleTransformer(ModuleInjectRule) transform xhtmlTemplate
    }


    val output = new StringBuilder


    output append """<?xml version="1.0" encoding="UTF-8"?>""" + "\n"
    output append """<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">""" + "\n"
    xhtmlTemplate map(node => Xhtml.toXhtml(x = node, sb = output, decodeEntities = true));


    response setCharacterEncoding "UTF-8"
    response setContentType "text/html; charset=utf-8"
    response setContentLength output.toString().getBytes.length

    response.getWriter print output

  }
}
