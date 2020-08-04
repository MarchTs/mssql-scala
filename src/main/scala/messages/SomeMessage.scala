package messages

import entities.SomeEntity
import facades.SomeFacade

case class SomeMessage(id: String, name: String, value: Int) extends Message

object SomeMessage {

  def from(someEntity: SomeEntity): SomeMessage = {
    SomeMessage(id = someEntity.id, name = someEntity.name, value = someEntity.value)
  }

}
