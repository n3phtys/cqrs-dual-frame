package org.nephtys.loom.generic.protocol.InternalStructures

import scala.scalajs.js.annotation.{JSExport, JSExportAll}

/**
  * Created by nephtys on 12/4/16.
  */
@JSExport
case class Email(email : String) extends AnyVal{

}

@JSExport
@JSExportAll
case class MetaInfo(owner : Email,
                    readers : Set[Email],
                    public : Boolean)