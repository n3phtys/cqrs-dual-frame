package org.nephtys.loom.generic.protocol.InternalStructures

import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success, Try}

/**
  * Created by nephtys on 12/4/16.
  */
@JSExport
case class FailableList[T](results : Seq[Either[T, String]]) {
  def asTry : Seq[Try[T]] = results.map {
    case Left(t) => Success(t)
    case Right(s) => Failure(new Exception(s))
  }
}
