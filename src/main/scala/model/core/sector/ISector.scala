package model.core.sector

import model.game.map.Coordinate
import model.game.map.system.{Affiliation, SectorType}

trait ISector:

  def location: Coordinate
  
  def affiliation: Affiliation
  
  def sectorType: SectorType
  
  def switchAffiliation(affiliation: Affiliation): ISector
  
  
