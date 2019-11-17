package dao.connector

import java.net.{InetAddress, InetSocketAddress}

import com.datastax.oss.driver.api.core.CqlSession

object CassandraConnector {
  // Default session for 127.0.0.1:9042
  val session: CqlSession = CqlSession.builder()
    .addContactPoint(new InetSocketAddress(InetAddress.getByName("172.19.0.2"), 9042))
    .withLocalDatacenter("datacenter1")
    .build()
  val keySpace = "vk"
}
