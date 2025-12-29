package org.cobalt.internal.ui.panel.panels

import java.awt.Color
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.internal.loader.AddonLoader
import org.cobalt.internal.ui.panel.UIPanel
import org.cobalt.internal.ui.panel.components.UIAddonEntry
import org.cobalt.internal.ui.panel.components.UITopbar

class UIAddons : UIPanel(
  x = 0F,
  y = 0F,
  width = 890F,
  height = 600F
) {

  private val topBar = UITopbar("Addons")
  private val entries = AddonLoader.getAddons().map { UIAddonEntry(it.first, it.second) }

  init {
    components.addAll(entries)
  }

  override fun render() {
    NVGRenderer.rect(x, y, width, height, Color(18, 18, 18).rgb, 10F)

    topBar
      .updateBounds(x, y)
      .render()


    var offsetX = x + 20F
    var offsetY = y + topBar.height + 20F

    entries[0]
      .updateBounds(offsetX, offsetY)
      .render()
  }

}
