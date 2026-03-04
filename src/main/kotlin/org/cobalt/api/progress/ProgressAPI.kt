package org.cobalt.api.progress

import org.cobalt.api.util.ui.helper.Image
import org.cobalt.internal.ui.progress.ProgressPosition

interface ProgressAPI {

  fun showProgress(progress: Int, color: Int, icon: Image? = null, position: ProgressPosition = ProgressPosition.TOP_CENTER): ProgressHandle
  fun updateProgress(handle: String, progress: Int)
  fun incrementProgress(handle: String, amount: Int)
  fun decrementProgress(handle: String, amount: Int)
  fun updateColor(handle: String, color: Int)
  fun removeProgress(handle: String)
  fun clear()

}
