name := "akka-http-rps"
organization := "none"

scalaVersion := "2.11.7"

libraryDependencies := {
  Seq("http-core", "http-experimental")
    .map(lib => "com.typesafe.akka" %% s"akka-$lib" % "2.4.2")
}
