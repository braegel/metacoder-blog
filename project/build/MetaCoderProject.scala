import sbt._
class MetaCoderProject(info : ProjectInfo) extends DefaultProject(info) {
  lazy val hi = task { println("oh hai!"); None }
  val log4j = "log4j" % "log4j" % "1.2.16"
}