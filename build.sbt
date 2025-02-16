import Dependencies._

ThisBuild / organization := "com.bpb.afp"
ThisBuild / scalaVersion := "3.6.3"

lazy val `appliedfunctionalprogramming` =
  project
    .in(file("."))
    .settings(name := "AppliedFunctionalProgramming")
    .settings(commonSettings)
    .settings(autoImportSettings)
    .settings(dependencies)

lazy val commonSettings = {
  lazy val commonScalacOptions =
    Seq(
      Compile / console / scalacOptions := {
        (Compile / console / scalacOptions)
          .value
          .filterNot(_.contains("wartremover"))
          .filterNot(Scalac.Lint.toSet)
          .filterNot(Scalac.FatalWarnings.toSet) :+ "-Wconf:any:silent"
      },
      Test / console / scalacOptions :=
        (Compile / console / scalacOptions).value,
    )

  lazy val otherCommonSettings =
    Seq(
      update / evictionWarningOptions := EvictionWarningOptions.empty
      // cs launch scalac:3.3.1 -- -Wconf:help
      // src is not yet available for Scala3
      // scalacOptions += s"-Wconf:src=${target.value}/.*:s",
    )

  Seq(
    commonScalacOptions,
    otherCommonSettings,
  ).reduceLeft(_ ++ _)
}

lazy val autoImportSettings =
  Seq(
    scalacOptions +=
      Seq(
        "java.lang",
        "scala",
        "scala.Predef",
        "scala.annotation",
        "scala.util.chaining",
      ).mkString(start = "-Yimports:", sep = ",", end = ""),
    Test / scalacOptions +=
      Seq(
        "org.scalacheck",
        "org.scalacheck.Prop",
      ).mkString(start = "-Yimports:", sep = ",", end = ""),
  )

lazy val dependencies =
  Seq(
    libraryDependencies ++= Seq(
    org.postgresql.postgresql, 
    org.typelevel.`cats-core`,
    org.typelevel.`cats-effect`,
    org.typelevel.`cats-free`,
    com.softwaremill.magnoloa1_3.magnolia,    
    dev.zio.zio,
    dev.zio.`zio-stream`,
    com.github.ghostdogpr.caliban,
    com.github.ghostdogpr.`caliban-zio-http`
    

      // main dependencies
    ),
    libraryDependencies ++= Seq(
      com.eed3si9n.expecty.expecty,
      org.scalacheck.scalacheck,
      org.scalameta.`munit-scalacheck`,
      org.scalameta.munit,
      org.typelevel.`discipline-munit`,
    ).map(_ % Test),
  )
