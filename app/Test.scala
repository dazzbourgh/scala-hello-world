import vk.methods.VkMethods.Streaming.openConnection

object Test {
  def main(args: Array[String]): Unit = {
    openConnection(println)
  }
}