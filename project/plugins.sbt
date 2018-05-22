logLevel := Level.Info

resolvers ++= Seq(
  Resolver.url("sbt-plugins", url("http://dl.bintray.com/zalando/sbt-plugins"))(Resolver.ivyStylePatterns),
  "zalando-bintray" at "https://dl.bintray.com/zalando/maven",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeRepo("releases"))

//addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")
//
//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.4")
//
////addSbtPlugin("de.zalando" % "sbt-api-first-hand" % "0.2.3")
//
//addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.1")


addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.3")

addSbtPlugin("de.zalando" % "sbt-api-first-hand" % "0.1.18")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")
