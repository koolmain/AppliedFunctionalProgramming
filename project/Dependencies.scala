import sbt._

object Dependencies {
  object com {
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

  object io {
    object quill {
        
       val `quill` =  "io.getquill" %% "quill-jdbc-zio" % "4.8.1"

    }
  }
 
}
