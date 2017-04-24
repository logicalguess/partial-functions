package pf.objects.functional

import pf.objects.domain.Behavior._
import pf.objects.domain._

class FunctionalEcho {
  private var state: State = State()

  def receive(event: Event): State = {
    state = logic(event, state) //could throw
    state
  }

  def getState: State = state
}
