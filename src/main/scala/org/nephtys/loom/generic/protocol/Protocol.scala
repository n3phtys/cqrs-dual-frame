package org.nephtys.loom.generic.protocol

import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, ID, IDable}

import scala.util.{Failure, Try}

/**
  * Created by nephtys on 12/4/16.
  */
trait Protocol[T <: Aggregate[T]] extends Ordered[Protocol[T]] {

  type Aggregates = Map[ID[T], T]

  val endpointRoot : EndpointRoot

  trait Command extends IDable[T, T]{
    def validate(user : Email, aggregate : Aggregates) : Try[Event] = {
      if (verifyAccess(user, aggregate)) {
        validateInternal(aggregate)
      } else {
        Failure(new IllegalAccessException)
      }
    }
    protected def validateInternal(aggregate : Aggregates) : Try[Event]

    //the following method is used for authorization on commands
    def verifyAccess(user : Email, aggregate : Aggregates) : Boolean = {
      aggregate.get(id).exists(_.owner.email == user.email)
    }
  }

  trait Event extends IDable[T, T]{
    def commit(aggregate : Aggregates) : Aggregates
  }

  override def compare(that: Protocol[T]): Int = {
      this.endpointRoot.prefix.compareTo(that.endpointRoot.prefix)
  }

}
