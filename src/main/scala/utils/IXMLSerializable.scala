package utils

import scala.xml.Elem

/**
 * Marks Components which are serializable as XML
 */

trait IXMLSerializable:

  /**
   * @return the XML representation of the Component
   */
  def toXML: Elem
