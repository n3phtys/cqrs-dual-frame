package org.nephtys.loom.generic.protocol

import org.nephtys.loom.generic.protocol.InternalStructures.{IDable, Owned}

/**
  * Created by nephtys on 12/4/16.
  */
trait Aggregate[T <: Aggregate[T]] extends IDable[T, Aggregate[T]] with Owned {

}
