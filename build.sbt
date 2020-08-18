import sbt.ThisBuild

val circeVersion = "0.12.1"
lazy val commonSettings = Seq(
  version := "0.1",
  organization := "com.example",
  scalaVersion := "2.13.3",
  name := "mssql-scala",
  test in assembly := {},
  sources in (Compile, doc) := Seq.empty,
  publishArtifact in (Compile, packageDoc) := false,
  scalacOptions := Seq("-feature", "-deprecation"),
  ThisBuild / turbo := true,
  fork in Test := true,
  parallelExecution in Test := false,
  libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % "3.3.2",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
    "com.microsoft.sqlserver" % "mssql-jdbc" % "8.4.0.jre8"
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka"          %% "akka-http"            % "10.1.11",
    "ch.megard"                  %% "akka-http-cors"       % "0.4.2",
    "de.heikoseeberger"          %% "akka-http-circe"      % "1.30.0",
    "io.circe"                   %% "circe-core"           % circeVersion,
    "io.circe"                   %% "circe-generic"        % circeVersion,
    "io.circe"                   %% "circe-parser"         % circeVersion,
    "io.circe"                   %% "circe-generic-extras" % circeVersion,
    "com.typesafe.scala-logging" %% "scala-logging"        % "3.9.2",
    "ch.qos.logback"              % "logback-classic"      % "1.2.3",
    "com.pauldijou"              %% "jwt-circe"            % "4.2.0",
    "com.beachape"               %% "enumeratum"           % "1.5.15",
    "com.beachape"               %% "enumeratum-circe"     % "1.5.22",
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11"  % Test,
    "com.typesafe.akka" %% "akka-testkit"      % "2.5.26"   % Test,
    "org.scalatest"     %% "scalatest"         % "3.2.0-M2" % Test,
    "com.h2database"     % "h2"                % "1.4.197"  % Test
  )
)

lazy val webServer = project.
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("MainServer"),
  )

lazy val mockApp = project.
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("Main"),
  )
