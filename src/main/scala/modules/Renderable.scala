package de.metacoder.blog.modules

import de.metacoder.blog.util.Logging
import xml.Node

trait Renderable extends Logging {

  private var renderHandler : PartialFunction[String, Node] = { case _ => <p>Render handler for {this} is not defined</p> }

  def render(url : String) = renderHandler(url)

  protected def onRender(handler : PartialFunction[String, Node]){
    renderHandler = handler;
  }

}