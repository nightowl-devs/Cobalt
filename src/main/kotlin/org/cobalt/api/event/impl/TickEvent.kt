package org.cobalt.api.event.impl

import org.cobalt.api.event.Event

abstract class TickEvent: Event() {
  class Start(): TickEvent()
  class End(): TickEvent()
}
