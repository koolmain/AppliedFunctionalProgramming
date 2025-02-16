import sbt._

object Dependencies {
  object com {

    object typesafe{
      object slick {
        val slick = "com.typesafe.slick" %% "slick" % "3.5.2"
        val `slick-test` = "com.typesafe.slick" %% "slick-testkit" % "3.5.2" % Test
        val `slick-hikaricp` = "com.typesafe.slick" %% "slick-hikaricp" % "3.5.2"

      }
    }
    object eed3si9n {
      object expecty {
        val expecty =
          "com.eed3si9n.expecty" %% "expecty" % "0.17.0"
      }
    }

    object softwaremill {
      object magnoloa1_3 {
        val `magnolia` = "com.softwaremill.magnolia1_3" %% "magnolia" % "1.2.0"
      }
    }

    object github {
      object ghostdogpr {
        val caliban = "com.github.ghostdogpr" %% "caliban" % "2.5.1"
        val `caliban-zio-http` ="com.github.ghostdogpr" %% "caliban-zio-http" % "2.5.1"
      }
    }

  }

  object org {

    object tpolecat {

  val `doobie-core` = "org.tpolecat" %% "doobie-core"      % "1.0.0-RC7"
  // And add any of these as needed
  val `doobie-h2` ="org.tpolecat" %% "doobie-h2"        % "1.0.0-RC7"          // H2 driver 1.4.200 + type mappings.
  val `doobie-hikari` ="org.tpolecat" %% "doobie-hikari"    % "1.0.0-RC7"          // HikariCP transactor.
  val `doobie-postgres` ="org.tpolecat" %% "doobie-postgres"  % "1.0.0-RC7"          // Postgres driver 42.7.5 + type mappings.
  val `doobie-specs2` ="org.tpolecat" %% "doobie-specs2"    % "1.0.0-RC7" % "test" // Specs2 support for typechecking statements.
  val `doobie-scalatest` ="org.tpolecat" %% "doobie-scalatest" % "1.0.0-RC7" % "test"  // ScalaTest support for typechecking statements.
 val `skunk-core` = "org.tpolecat" %% "skunk-core" % "0.6.4"
  }

    object scalacheck {
      val scalacheck =
        "org.scalacheck" %% "scalacheck" % "1.18.1"
    }

    object postgresql{
      val postgresql = "org.postgresql" % "postgresql" % "42.7.5"
    }
    
    object scalameta {
      val munit =
        moduleId("munit")

      val `munit-scalacheck` =
        moduleId("munit-scalacheck")

      private def moduleId(artifact: String): ModuleID =
        "org.scalameta" %% artifact % "1.1.0"
    }

    object typelevel {
      val `discipline-munit` =
        "org.typelevel" %% "discipline-munit" % "2.0.0"
      
      val `cats-core` =
        "org.typelevel" %% "cats-core" % "2.13.0"

      val `cats-effect` =
        "org.typelevel" %% "cats-effect" % "3.5.7"

      val `cats-free` =  "org.typelevel" %% "cats-free" % "2.10.0"
    }    
  }

  object dev {
      object zio{
        val `zio`= "dev.zio" %% "zio" % "2.1.15"

        val `zio-stream`=  "dev.zio" %% "zio-streams" % "2.1.15"
      }
    }

  object co {
    object fs2 {
      val `fs2-core` = "co.fs2" %% "fs2-core" % "3.11.0"
      val `fs2-io` = "co.fs2" %% "fs2-io" % "3.11.0"
      val `fs2-reactive-streams` = "co.fs2" %% "fs2-reactive-streams" % "3.11.0"
    }
  }

  object ioq {
    object quill {        
       val `quill` =  "io.getquill" %% "quill-jdbc-zio" % "4.8.6"
    }
  }


 
}
