package org.nephtys.loom.generic.protocol.InternalStructures

/**
  * Created by nephtys on 12/4/16.
  */
trait Owned {

  def owner : Email
  def public : Boolean
  def readers : Set[Email]



}
