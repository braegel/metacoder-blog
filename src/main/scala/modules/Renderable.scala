package de.metacoder.blog.modules

import de.metacoder.blog.util.Logging
import xml.Elem
import java.lang.Boolean

trait Renderable extends Logging {

  val matchingRule : MatchingRule



  def elementMatches(elem : Elem): Boolean = matchingRule elementMatches elem


  private var renderHandler : PartialFunction[String, Elem] = { case _ => <p>Render handler for {this} is not defined</p> }

  def render(url : String) = renderHandler(url)

  protected def onRender(handler : PartialFunction[String, Elem]){
    renderHandler = handler;
  }

}