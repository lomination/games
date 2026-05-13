val scala3Version = "3.8.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "games",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    // semanticdbEnabled := true,
    // semanticdbVersion := scalafixSemanticdb.revision,

    scalacOptions ++= Seq(
      "-encoding", "utf8",
      "-Werror",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xsemanticdb",
      "-Wunused:imports"
    ),

    libraryDependencies += "org.scalameta" %% "munit" % "1.3.0" % Test,
    libraryDependencies += "org.jline" % "jline" % "3.25.1"
  )
