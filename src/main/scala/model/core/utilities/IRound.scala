package model.core.utilities

trait IRound:

  def value: Int
  
  def next: IRound

  def decrease: Option[IRound]