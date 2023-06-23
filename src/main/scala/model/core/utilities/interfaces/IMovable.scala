package model.core.utilities.interfaces

import model.core.mechanics.IMoveVector

trait IMovable:

  def getMove: IMoveVector
  
  def move: Option[IMoveVector]
