package messages.system

import messages.Message

final case class ErrorMessage(id: Int, error: String) extends Message
