package org.cobalt.internal.ui.panel.components

import java.awt.Color
import org.cobalt.api.addon.Addon
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.internal.loader.AddonLoader
import org.cobalt.internal.ui.UIComponent

class UIAddonEntry(
  val metadata: AddonLoader.AddonMetadata,
  val addon: Addon,
) : UIComponent(
  x = 0F,
  y = 0F,
  width = 200F,
  height = 120F,
) {

  override fun render() {
    NVGRenderer.hollowRect(
      x, y,
      width, height,
      2F, Color(30, 30, 30).rgb,
      10F
    )

    NVGRenderer.rect(
      x, y,
      width, height,
      Color(30, 30, 30, 150).rgb,
      10F
    )
  }

}
