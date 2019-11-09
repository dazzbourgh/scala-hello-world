package vk.http

import play.api.http.{ContentTypes, HeaderNames}
import play.api.libs.json.{Json, Reads}
import scalaj.http.{Http, HttpOptions}

trait Exchanging {

  import vk.methods.VkMethods.{accessToken, v}

  def exchange[ReqBody, RespBody](url: String,
                                  params: Map[String, String] = Map.empty,
                                  body: ReqBody = null,
                                  method: String)
                                 (implicit reads: Reads[RespBody]): RespBody = {
    var req = Http(url)
      .header(HeaderNames.CONTENT_TYPE, ContentTypes.JSON)
      .param("v", v)
      .param("access_token", accessToken)
    for ((key, value) <- params) {
      req = req.param(key, value)
    }
    req.option(HttpOptions.method(method))
    if (body != null) req = req.postData(body.toString)
    val response = req.asString.body
    Json.parse(response).validate[RespBody].get
  }
}

trait RequestProperties {
  protected def url: String

  protected def params: Map[String, String]
}

trait HttpRequests extends Exchanging with RequestProperties {

  def get[RespBody](implicit reads: Reads[RespBody]) = exchange[Null, RespBody](url, params, method = "GET")

  def post[ReqBody, RespBody](body: ReqBody = null)(implicit reads: Reads[RespBody]) = exchange(url, params, body, "POST")

  def delete[ReqBody, RespBody](body: ReqBody = null)(implicit reads: Reads[RespBody]) = exchange(url, params, body, "DELETE")
}
