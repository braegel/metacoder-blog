package de.metacoder.blog.modules

import xml.Node

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 29.05.11
 * Time: 17:44
 */

trait Renderable {

  def render(url : String) : Node
}