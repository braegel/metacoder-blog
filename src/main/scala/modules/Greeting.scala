package de.metacoder.blog.modules

import xml.Node

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 29.05.11
 * Time: 17:48
 */

class Greeting extends Renderable  {
  def render(url : String) : Node = {
    <h3>Hello World, you called the url: {url}</h3>
  }
}