package de.metacoder.blog.entities {

	import java.util.Date

	case class Entry(
		id : Long, 
		date : Date,
		author : Author,
		title : String,
		content : String,
		comments : List[Comment]
	)

	case class Comment(
		id : Long, 
		date : Date, 
		author : String, 
		title : String, 
		content : String
	)

	case class Author(
		id : Long, 
		name : String,
		email : String, 
		realName : String, 
		description : String, 
		password : String
	)
}
