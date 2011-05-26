import sbt._

class MetaCoderProject(info : ProjectInfo) extends DefaultWebProject(info) {
  val log4j = "log4j" % "log4j" % "1.2.16"

  val jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.1.6.v20100715" % "test"
  val servletApi = "javax.servlet" % "servlet-api" % "2.5" % "provided"
}