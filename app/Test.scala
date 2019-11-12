import vk.dtos.VkDtos.RulesDto
import vk.methods.VkMethods.Rules
import vk.methods.VkMethods.Streaming.openConnection

object Test {
  def main(args: Array[String]): Unit = {
    println(Rules().get[RulesDto])
    openConnection(println)
  }
}