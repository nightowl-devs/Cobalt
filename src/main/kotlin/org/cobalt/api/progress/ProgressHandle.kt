package org.cobalt.api.progress

import org.cobalt.api.util.ui.helper.Image
import org.cobalt.internal.ui.progress.ProgressManager

class ProgressHandle(private val manager: ProgressManager, private val internalHandle: String) {

  fun setProgress(progress: Int) {
    manager.updateProgress(internalHandle, progress)
  }

  fun increment(amount: Int = 1) {
    manager.incrementProgress(internalHandle, amount)
  }

  fun decrement(amount: Int = 1) {
    manager.decrementProgress(internalHandle, amount)
  }

  fun updateColor(newColor: Int) {
    manager.updateColor(internalHandle, newColor)
  }

  fun hide() {
    manager.removeProgress(internalHandle)
  }

}
