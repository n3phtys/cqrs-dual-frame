package org.nephtys.loom.shared.api.InternalDataStructures

import org.nephtys.loom.shared.api.InternalDataStructures.HttpResults.{ETag, ResultCode}

/**
  * Created by nephtys on 12/4/16.
  */
case class HttpResult(resultCode : ResultCode, body : String, etag : Option[ETag])


