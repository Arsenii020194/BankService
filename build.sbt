name := """BankService"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, LauncherJarPlugin)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += "org.hibernate" % "hibernate-annotations" % "3.5.6-Final"
libraryDependencies += "org.julienrf" %% "play-jsmessages" % "2.0.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.2"
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"
libraryDependencies += "org.webjars" % "requirejs" % "2.2.0"
libraryDependencies += "com.openhtmltopdf" % "openhtmltopdf-pdfbox" % "0.0.1-RC13"
libraryDependencies += "com.openhtmltopdf" % "openhtmltopdf-jsoup-dom-converter" % "0.0.1-RC13"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.2"
libraryDependencies += "commons-io" % "commons-io" % "2.6"
