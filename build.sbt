import com.typesafe.sbt.SbtScalariform
import sbt._
import Keys._


lazy val ver = "0.0.1-SNAPSHOT"

lazy val eventuateVerison = "0.8.1"
lazy val scalaVer = "2.12.6"
lazy val AkkaVersion = "2.5.12"
lazy val gRpcVersion = "1.12.0"
lazy val scalapbJson4sVersion = "0.2.0"
lazy val slickVersion = "3.2.3"
lazy val commonsVersion = "0.0.1"

maintainer := "dutyk <>"
packageSummary := s"EXCHANGE ${ver}"

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val commonSettings = Seq(
  organization := "io.kang.exchange",
  version := ver,
  scalaVersion := {scalaVer},
  scalacOptions ++= Seq("-encoding", "utf8"),
  scalacOptions ++= Seq("-optimize"),
  javacOptions ++= Seq("-encoding", "UTF-8"),
  publishArtifact := true,
  publishArtifact in (Compile, packageSrc) := false,
  publishArtifact in (Compile, packageDoc) := false,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    Resolver.url("sbt-plugins", url("http://dl.bintray.com/zalando/sbt-plugins"))(Resolver.ivyStylePatterns),
    "zalando-bintray" at "https://dl.bintray.com/zalando/maven",
    "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    "terracotta-releases" at "http://www.terracotta.org/download/reflector/releases/",
    "jeffmay" at "https://dl.bintray.com/jeffmay/maven"),
  libraryDependencies ++= Seq(
    "org.specs2" %% "specs2-core" % "4.1.0" % "test",
    "com.github.scopt" %% "scopt" % "3.5.0",
    "org.slf4s" %% "slf4s-api" % "1.7.25",
    "commons-codec" % "commons-codec" % "1.10",
    "net.codingwell" %% "scala-guice" % "4.1.0",
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "mysql" % "mysql-connector-java" % "5.1.22",
    "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
    "com.google.code.gson" % "gson" % "2.8.0",
    "org.apache.httpcomponents" % "httpclient" % "4.3.3",
    "com.typesafe.akka" %% "akka-stream-kafka" % "0.15",
    "ch.qos.logback" % "logback-classic" % "1.1.2")) ++
  inConfig(Compile)(
    (sourceDirectories in Global in scalariformFormat) := unmanagedSourceDirectories.value)

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val scalapbSettings = Seq(
  libraryDependencies ++= Seq(
    "com.google.protobuf" % "protobuf-java" % "3.1.0",
    "io.grpc" % "grpc-netty" % gRpcVersion,
    "io.grpc" % "grpc-protobuf" % gRpcVersion,
    "io.grpc" % "grpc-stub" % gRpcVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion),

  PB.targets in Compile := Seq(
    scalapb.gen(
      flatPackage = false,
      javaConversions = false,
      grpc = true,
      singleLineToProtoString = false) -> (sourceManaged in Compile).value))

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val akkaClusterSettings = Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-cluster-tools" % AkkaVersion,
    "com.typesafe.akka" %% "akka-contrib" % AkkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % AkkaVersion))


lazy val root = (project in file("."))
  .settings(name := "exchange")
  .aggregate(
    protoExmarket,
    exmarket)

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val protoExmarket = (project in file("proto/exmarket"))
  .settings(commonSettings: _*)
  .settings(scalapbSettings: _*)
  .settings(publishArtifact := true)

///////////////////////////////////////////////////////////////////////////////////////////////////

lazy val exmarket = (project in file("exmarket"))
  //.settings(SbtScalariform.scalariformSettings: _*)
  .settings(SbtScalariform.scalariformSettings: _*)
  .enablePlugins(JavaAppPackaging)
  .dependsOn(protoExmarket)
  .settings(commonSettings: _*)
  .settings(scalapbSettings: _*)
  .settings(akkaClusterSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % "10.1.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.0"))

///////////////////////////////////////////////////////////////////////////////////////////////////
