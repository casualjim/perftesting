organization := "org.scalatra"

name := "jetty-perftest"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.3.v20120416",
  "org.eclipse.jetty.orbit" % "javax.servlet"      % "3.0.0.v201112011016" artifacts(Artifact("javax.servlet", "orbit", "jar")),
  "javax.servlet" % "javax.servlet-api"  % "3.0.1" 
)

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
