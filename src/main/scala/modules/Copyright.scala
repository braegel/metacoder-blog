package de.metacoder.blog.modules

import java.util.Date
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 13.06.11
 * Time: 23:05
 */

class Copyright extends Renderable {

  val matchingRule = IdMatchingRule("copyrightModule")

  onRender {
    case _ => {
      val currentYear = new SimpleDateFormat("yyyy") format (new Date)
      <address id="copyright">&copy; {if (currentYear != "2011") "2011 - "}{currentYear} by Benjamin Neff &amp; Felix Becker</address>
    }
  }
}