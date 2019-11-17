package dao

import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.querybuilder.QueryBuilder._
import dao.connector.CassandraConnector._
import models.Event

import scala.jdk.CollectionConverters._

class EventDao {

  def getContainingText(text: String): List[Event] = EventDao map session.execute(
    selectFrom(keySpace, "event")
      .columns("event_url", "text", "action", "tags")
      .whereColumn("text")
      .like(bindMarker())
      .build(s"%$text%"))

  def save(event: Event): Unit = session.execute(
    insertInto(keySpace, "event")
      .json(bindMarker())
      .build(event.toString))
}

object EventDao {
  def map(resultSet: ResultSet): List[Event] = {
    resultSet.asScala.toList.map { row =>
      Event(row.getString("event_url"),
        row.getString("text"),
        row.getString("action"),
        row.getList("tags", classOf[String]).toArray(Array()))
    }
  }
}
