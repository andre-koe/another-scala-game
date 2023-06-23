package utils

import scala.xml.Elem

trait IXMLSerializable:

  def toXML: Elem
