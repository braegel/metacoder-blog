package de.metacoder.blog.modules

import xml.Node

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 13.06.11
 * Time: 22:51
 */

class Sample extends Renderable {
  override def render(url : String) : Node = {
    <span><marquee>I am the Node of the sample module!</marquee></span> // lol, DHTML
  }
}