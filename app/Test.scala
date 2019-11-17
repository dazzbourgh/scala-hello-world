import dao.EventDao

object Test {
  def main(args: Array[String]): Unit = {
    val eventDao = new EventDao()
    //    openConnection(eventDao.save)
    eventDao.getContainingText("test")
      .foreach(println)
  }
}
