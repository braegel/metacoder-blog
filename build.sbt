name := "metacoder-blog"

version := "0.1"

organization := "de.metacoder"

scalaVersion := "2.9.0"

// webproject
seq(WebPlugin.webSettings :_*)

// dependencies
libraryDependencies += "log4j" % "log4j" % "1.2.16"

libraryDependencies ++= Seq(
	"org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "jetty",
	"javax.servlet" % "servlet-api" % "2.5" % "provided"
)