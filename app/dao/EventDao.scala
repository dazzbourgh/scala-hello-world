package dao

import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.querybuilder.QueryBuilder._
import dao.connector.CassandraConnector._
import models.Event

class EventDao {

  //  private val saveOne = quote {
  //    event: Event => {
  //      query[Event].insert(lift(event))
  //    }
  //  }

  def getContainingText(text: String): Option[Event] = EventDao map session.execute(
    selectFrom(keySpace, "event")
      .columns("event_url", "text", "action", "tags")
      .whereColumn("text")
      .like(bindMarker())
      .build(s"%$text%"))

  //  def save(event: Event) = ctx.run(saveOne)
}

object EventDao {
  def map(resultSet: ResultSet): Option[Event] = {
    Option(resultSet.one())
      .map { row =>
        Event(row.getString("event_url"),
          row.getString("text"),
          row.getString("action"),
          row.getList("tags", classOf[String]).toArray(Array()))
      }
  }
}
