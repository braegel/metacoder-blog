package de.metacoder.blog.modules

import xml.Elem

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 24.06.11
 * Time: 04:30
 */

abstract class MatchingRule {
  def elementMatches(elem : Elem) : Boolean;
}

case class TagMatchingRule(tagName : String) extends MatchingRule {
  def elementMatches(elem : Elem) = elem.label == tagName
}

case class IdMatchingRule(id : String) extends MatchingRule {
  def elementMatches(elem : Elem) = elem.attributes.get("id").exists(_.text == id)
}