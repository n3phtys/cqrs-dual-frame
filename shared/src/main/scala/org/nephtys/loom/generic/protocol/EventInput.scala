package org.nephtys.loom.generic.protocol

/**
  * Created by nephtys on 1/1/17.
  */
object EventInput {

  sealed trait EventInput {
    def get[T] : T
  }
  case object NonExisting extends EventInput {
    override def get[B]: B = throw new UnsupportedOperationException
  }
  final case class Update[T](t : T) extends EventInput {
    override def get[B]: B = t.asInstanceOf[B]
  }
  final case class Removed[T](t : T) extends EventInput {
    override def get[B]: B = t.asInstanceOf[B]
  }

}
