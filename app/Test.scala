import dao.EventDao
import vk.methods.VkMethods.Streaming.openConnection

object Test {
  def main(args: Array[String]): Unit = {
    val eventDao = new EventDao()
    openConnection(eventDao.save, println)
  }
}
