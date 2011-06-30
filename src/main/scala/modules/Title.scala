package de.metacoder.blog.modules


/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 13.06.11
 * Time: 23:05
 */

class Title extends Renderable {

 val matchingRule = TagMatchingRule("title")

 onRender {
   case url : String => {
     <title>Metacoder.de - Because it's fun</title>
   }
 }
}