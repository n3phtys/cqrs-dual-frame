package org.nephtys.loom.shared.api.client

import org.nephtys.loom.generic.protocol.InternalStructures.EndpointRoot
import org.nephtys.loom.shared.api.InternalDataStructures.HttpResult
import org.nephtys.loom.shared.api.InternalDataStructures.HttpResults.ETag

import scala.concurrent.Future

/**
  * Created by nephtys on 12/4/16.
  */
trait HttpService {


  def post(url : String, json : String)(implicit tokenService: TokenService) : Future[HttpResult]
  def get(url : String, endpointRoot : Option[EndpointRoot] = None)(implicit tokenService: TokenService) : Future[HttpResult]



  val timeoutMs : Int

  val authenticatedHeaderName : String = "Authorization"
  val modifiedETagHeader : String = "If-None-Match"

  protected def buildHeaders(endpointRoot : Option[EndpointRoot])(implicit tokenService: TokenService) : Map[String, String] = {
      endpointRoot.flatMap(root => getETag(root)).map[Map[String,String]](etag => Map(modifiedETagHeader -> etag.content)).getOrElse(Map.empty[String, String]) ++ tokenService.currentTokenWithBearer.map(token => Map(authenticatedHeaderName -> token)).getOrElse(Map.empty[String, String]) + CSRF.xforwardheader + CSRF.xrequestheader
  }


  def getETag(endpontroot : EndpointRoot) : Option[ETag]
  def setETag(endpontroot : EndpointRoot, etag : ETag) : Unit

  def currentHost : String
  def currentProtocol : String

  protected object CSRF {
    def calculateCurrentOrigin : String = currentProtocol +"//"+currentHost

    protected def XForwardedHostHeader = """X-Forwarded-Host"""

    protected def OriginHeader = """Origin""" //implicitly filled by browser

    protected def XRequestedWithHeader = """X-Requested-With"""

    protected def XRequestedWithValue = """XMLHttpRequest"""

    def xforwardheader : (String, String) = XForwardedHostHeader -> calculateCurrentOrigin
    def xrequestheader  : (String, String) = XRequestedWithHeader -> XRequestedWithValue
  }

}
