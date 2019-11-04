package models

object VkModels {

  case class Rule(value: String, tag: String) extends JsonLike

  case class RuleResponse(rule: Rule) extends JsonLike

  def doStuff(block: Int => Int) = {
    val name = 7
    block(name)
  }

  def stuff1(num: Int)(implicit i: Int) = {
    num + i + 1
  }

  def main(args: Array[String]): Unit = {
    println(doStuff { implicit a =>
      stuff1(5)
    })
  }
}
