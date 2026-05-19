val scala3Version = "3.8.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "games",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    assembly / mainClass := Some("lomination.games.main"),
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "versions", _, "module-info.class") => MergeStrategy.discard
      case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.first
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    },

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
