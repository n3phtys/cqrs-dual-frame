package org.nephtys.loom.shared.api.InternalDataStructures

import org.nephtys.loom.shared.api.InternalDataStructures.HttpResults.{ETag, ResultCode}

import scala.scalajs.js.annotation.JSExport

/**
  * Created by nephtys on 12/4/16.
  */
@JSExport
case class HttpResult(resultCode : ResultCode, body : String, etag : Option[ETag])


