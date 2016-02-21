name := "akka-http-rps"
organization := "none"

scalaVersion := "2.11.7"

libraryDependencies := {
  val compile =
    Seq("http-core", "http-experimental")
      .map(lib => "com.typesafe.akka" %% s"akka-$lib" % "2.4.2")

  val test =
    Seq("org.specs2" %% "specs2-core" % "3.7.1" % "test")

  compile ++ test
}
