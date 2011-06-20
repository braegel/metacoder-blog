package de.metacoder.blog.entities

import java.util.Date
import xml.Node

case class Entry(
  id : Long,
  date : Date,
  authorId : Long,
  authorName : String,
  title : String,
  content : String,
  comments : Map[Long, Comment]
)

case class Comment(
  id : Long,
  date : Date,
  authorName : String,
  content : String
)

case class Author(
  id : Long,
  name : String,
  email : String,
  description : String,
  password : String
)
