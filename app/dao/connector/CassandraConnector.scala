package dao.connector

import java.net.InetAddress.getByName
import java.net.InetSocketAddress

import com.datastax.oss.driver.api.core.CqlSession
import config.AppConfig.config

object CassandraConnector {
  val session: CqlSession = CqlSession.builder()
    .addContactPoint(new InetSocketAddress(
      getByName(
        config.getString("db.address")),
      config.getInt("db.port")))
    .withLocalDatacenter("datacenter1")
    .build()
  val keySpace: String = config.getString("db.keyspace")
}
