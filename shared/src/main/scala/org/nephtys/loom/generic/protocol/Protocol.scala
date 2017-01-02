package org.nephtys.loom.generic.protocol

import org.nephtys.loom.generic.protocol.EventInput.{EventInput, NonExisting, Removed, Update}
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, ID, IDable}

import scala.util.{Failure, Success, Try}

/**
  * Created by nephtys on 12/4/16.
  */
trait Protocol[T <: Aggregate[T]] extends Ordered[Protocol[T]] {

  type Aggregates = Map[ID[T], T]

  val endpointRoot : EndpointRoot

  trait Command extends IDable[T, T]{
    def insert : Boolean = false
    def remove : Boolean = false

    def findAggregate(aggregates: Aggregates) : Try[T] = {
      aggregates.get(id).map(t => Success(t)).getOrElse(Failure(new NoSuchElementException))
    }

    //has to be overwritten by Creation Event
    def checkCreatorIsAuthor(requester : Email) : Boolean = throw new UnsupportedOperationException

    def validate(user : Email, aggregate : Aggregates) : Try[Event] = {
      if (insert) {
        if (!aggregate.contains(id)) {
          if (checkCreatorIsAuthor(user)) {
            validateInternal(NonExisting)
          } else {
            Failure(new IllegalAccessException)
          }
        } else {
          Failure(new Exception("Key already in use"))
        }
      } else {
        //get previous aggregate
        findAggregate(aggregate).flatMap(t => {

          //check if user can access
          if (verifyAccess(user, t)) {

            //decide if remove
            if (remove) {
              validateInternal(Removed[T](t))
            } else {
              validateInternal(Update[T](t))
            }

          } else {
            Failure(new IllegalAccessException)
          }

        })
      }
    }

    protected def validateInternal(input : EventInput) : Try[Event]

    //the following method is used for authorization on commands
    def verifyAccess(user : Email, aggregate : T) : Boolean = {
      aggregate.owner.email == user.email
    }
  }



  trait Event extends IDable[T, T]{

    //has to be overwritten by Creation Event
    def insert : Boolean = false

    //has to be overwritten by Deletion Event
    def remove : Boolean = false

    //has to be overwritten by Creation Event
    def createNew : T = throw new UnsupportedOperationException

    //has to be overwritten by Deletion Event
    def changesAfterRemoval(aggregates: Aggregates) : Aggregates = aggregates

    def commit(aggregate : Aggregates) : Aggregates =  {
      if (insert) {
        val t = createNew
        aggregate.+((t.id, t))
      } else if (remove) {
        changesAfterRemoval(aggregate.-(id))
      } else {
          aggregate.get(id).map(t1 => {
          val t2 = commitInternal(Update(t1))
          aggregate.+((t2.id, t2))
        }).getOrElse(aggregate)
      }
    }


    def commitInternal(input : EventInput) : T
  }

  override def compare(that: Protocol[T]): Int = {
      this.endpointRoot.prefix.compareTo(that.endpointRoot.prefix)
  }

}
