package org.nephtys.loom.generic.protocol

import org.nephtys.loom.generic.protocol.InternalStructures.FailableList

import scala.language.higherKinds

/**
  * Created by nephtys on 12/4/16.
  */
trait Backend[Agg <: Aggregate] {

  protocol : Protocol[Agg] =>

  def readCommands(json : String) : Seq[Command]
  def writeAggregates(aggregates: Seq[Agg]) : String
  def writeFailableList(failableList : FailableList[Event]) : String

}
