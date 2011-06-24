package de.metacoder.blog.modules

import java.util.Date
import java.text.SimpleDateFormat

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
     val currentYear = new SimpleDateFormat("yyyy") format (new Date)
     <title>Metacoder.de {currentYear} by Benjamin Neff and Felix Becker</title>
   }
 }

}