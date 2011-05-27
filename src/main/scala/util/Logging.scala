package de.metacoder.blog.util

import org.apache.log4j.Logger

trait Logging {
  lazy val logger : Logger = Logger getLogger getClass.getName
}