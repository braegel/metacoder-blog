package de.metacoder.blog.servlets

import _root_.de.metacoder.blog.modules._
import de.metacoder.blog.util.Logging
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import xml.parsing.XhtmlParser
import xml.transform.{RuleTransformer, RewriteRule}
import xml._


class MetacoderServlet extends HttpServlet with Logging {

  val modules : List[(String, Renderable)] = List(
    (".*", new Copyright),
    ("/blog.highspeed", new BlogPosts),
    ("/about.highspeed", new About),
    (".*", new Title)
  )

  override def doGet(request : HttpServletRequest, response : HttpServletResponse) : Unit = {
    logger debug "loading xhtml template"
    var xhtmlTemplate = XhtmlParser(scala.io.Source.fromURL(getServletContext.getResource("/templates/blog.xhtml")))

    for((urlPattern, module) <- modules){

      if(request.getRequestURI.matches(urlPattern)){

        object ModuleInjectRule extends RewriteRule {
          override def transform(n: Seq[Node]): Seq[Node] = n match {
            case elem : Elem if module.elementMatches(elem) => module.render(request.getRequestURI.toString)
            case other => other
          }
        }
        xhtmlTemplate = new RuleTransformer(ModuleInjectRule) transform xhtmlTemplate
      }
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
