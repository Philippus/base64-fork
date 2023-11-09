organization := "nl.gn0s1s"

javaOptions in Test += "-Dio.netty.leakDetectionLevel=advanced"

fork in Test := true

name := "base64"

version := "0.2.2"

licenses := Seq(
  ("MIT", url(s"https://github.com/softprops/${name.value}/blob/${version.value}/LICENSE")))

homepage := Some(url(s"https://github.com/softprops/${name.value}/#readme"))

scalacOptions += Opts.compile.deprecation

crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.18", "2.13.12")

scalaVersion := crossScalaVersions.value.last

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-funspec" % "3.2.17" % Test,
  "commons-codec" % "commons-codec" % "1.16.0" % Test,
  "io.netty" % "netty-codec" % "4.1.101.Final" % Test)

bintrayOrganization := Some("gn0s1s")
bintrayRepository := "releases"

lsSettings

LsKeys.tags in LsKeys.lsync := (bintrayPackageLabels in bintray).value

externalResolvers in LsKeys.lsync := (resolvers in bintray).value

cappiSettings

pomExtra  :=
  <scm>
    <url>git@github.com:Philippus/{name.value}.git</url>
    <connection>scm:git:git@github.com:Philippus/{name.value}.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>
