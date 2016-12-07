package org.nephtys.loom.generic.protocol

import org.nephtys.loom.generic.protocol.InternalStructures.FailableList

/**
  * Created by nephtys on 12/4/16.
  */
trait FrontendSerializableProtocol[Agg <: Aggregate[Agg]] {

  protocol : Protocol[Agg] =>


  def writeCommands(commands : Seq[Command]) : String
  def readAggregates(json : String) : Seq[Agg]
  def readFailableList(json : String) : FailableList[Event]

}
