organization := "org.scalatra"

name := "scalatra-perftest"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "io.netty" % "netty" % "3.5.3.Final",
  "NettyExtension" % "NettyExtension" % "1.1.13"
)

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "GoldenGate" at "http://openr66.free.fr/maven2"
)
