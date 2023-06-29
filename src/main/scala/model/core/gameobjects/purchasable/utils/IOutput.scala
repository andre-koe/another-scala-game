package model.core.gameobjects.purchasable.utils

import model.core.utilities.{ICapacity, IResourceHolder}

/** Interface for Output functionality.
 *
 *  This trait represents the output of a resource/capacity production process of a building
 */
trait IOutput {

  /** Getter for the ResourceHolder object that represents the resources that are being outputted.
   *
   *  @return IResourceHolder: Object representing the resources being outputted.
   */
  def rHolder: IResourceHolder

  /** Getter for the Capacity object that represents the capacity of the output process.
   *
   *  @return ICapacity: Object representing the capacity of the output process.
   */
  def cap: ICapacity

  /** Method to increase the current output with another output.
   *
   *  @param other: IOutput: The other output that should be added to the current output.
   *  @return IOutput: The new output after the addition.
   */
  def increase(other: IOutput): IOutput
}