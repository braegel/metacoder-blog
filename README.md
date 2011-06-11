# Build-Instruktionen Metacoder-Blog 0.1 - Stand 11.06.2011

Dieses Projekt wird gebaut mit:

* Scala 2.9.0
* SBT 0.10.0

Um aus dem SBT-Projekt ein IDEA-Project zu generieren, welches man mit der IntelliJ IDEA IDE oeffnen kann,
muss man folgende Schritte durchfuehren:

~/.sbt/plugins/build.sbt

	libraryDependencies += "org.sbtidea" %% "xsbt-idea" % "0.1"

Und bis das idea-plugin in einem Maven Repo ist:

~/.sbt/plugins/project/Build.scala

	import sbt._

	object MyPlugins extends Build {
  		lazy val root = Project("root", file(".")) dependsOn (uri("git://github.com/ijuma/sbt-idea.git#sbt-0.10"))
	}

danach `sbt` starten und `gen-idea` ausfuehren

Das sorgt dafuer, dass entsprechende *.iml-Projektdateien fuer IntelliJ IDEA generiert werden.
