resolvers += Resolver.url(
  "bintray-sbt-plugin-releases",
    url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
        Resolver.ivyStylePatterns)

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.6")

addSbtPlugin("me.lessis" % "ls-sbt" % "0.1.3")

addSbtPlugin("me.lessis" % "cappi" % "0.1.1")
