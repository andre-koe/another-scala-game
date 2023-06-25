package model.core.utilities

import model.core.board.sector.sectorutils.Affiliation

trait IAffiliated:
  
  def affiliation: Affiliation
