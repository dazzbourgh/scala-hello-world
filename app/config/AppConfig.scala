package config

import com.typesafe.config.{Config, ConfigFactory}

object AppConfig {
  val config: Config = ConfigFactory.load("application.conf")
}
