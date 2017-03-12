val akkaStreamsVersion = "2.4.16"
val scalazVersion = "7.2.8"

scalaVersion in ThisBuild := "2.12.1"

lazy val `dev-tools` = project.in(file("."))
  .settings(version := "1.0")

lazy val `github-client` = project.dependsOn(`scala-async-http-client`)
lazy val `teamcity-client` = project.dependsOn(`scala-async-http-client`)
lazy val `scala-async-http-client` = RootProject(uri("git://github.com/ghaxx/scala-async-http-client.git"))

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaStreamsVersion,
  //  "com.typesafe.akka" %% "akka-typed-experimental" % akkaStreamsVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaStreamsVersion,
  "org.json4s" %% "json4s-native" % "3.5.0",
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-concurrent" % scalazVersion,
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.8",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaStreamsVersion,
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalaj" %% "scalaj-http" % "2.3.0"
)

resolvers += "Akka Snapshot Repository" at "pl.http://repo.akka.io/snapshots/"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)
resolvers += "spray repo" at "pl.http://repo.spray.io"