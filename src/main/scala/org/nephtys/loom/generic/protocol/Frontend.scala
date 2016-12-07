package org.nephtys.loom.generic.protocol

import org.nephtys.loom.generic.protocol.InternalStructures.{FailableList, GuiWidgetMockClass}

import scala.language.higherKinds

/**
  * Created by nephtys on 12/4/16.
  */
trait Frontend[Agg <: Aggregate[Agg]] {

  protocol : FrontendSerializableProtocol[Agg] with Protocol[Agg] =>


  def newDialogInstance : GuiWidgetMockClass
  def editDialogInstance : GuiWidgetMockClass
  def mainTableHeader : GuiWidgetMockClass

  def transformToTableRow(aggregate : Agg) : GuiWidgetMockClass
  def initializeEditDialogWith(aggregate: Agg) : Unit

}
