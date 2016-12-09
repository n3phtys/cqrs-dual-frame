package org.nephtys.loom.generic.protocol.InternalStructures

/**
  * Created by nephtys on 12/4/16.
  */
trait IDable[A <: T, +T] {
  def id : ID[A]
}
