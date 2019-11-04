package models

object ClientModels {

  final case class Response(currency: String,
                            mentions: Long,
                            ratio: Double,
                            timestamp: Long) extends JsonLike

  final case class Request(from: Long, to: Long)

}
