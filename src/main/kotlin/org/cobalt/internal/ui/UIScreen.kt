package org.cobalt.internal.ui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import org.cobalt.api.util.TickScheduler
import org.cobalt.api.util.ui.NVGSpecialRenderer

internal abstract class UIScreen : Screen(Component.empty()) {

  protected val mc: Minecraft =
    Minecraft.getInstance()

  fun openUI() =
    TickScheduler.schedule(1) { mc.setScreen(this) }

  abstract fun renderNVG()

  override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
    NVGSpecialRenderer.draw(guiGraphics, 0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight()) {
      renderNVG()
    }

    super.render(guiGraphics, i, j, f)
  }

  override fun isPauseScreen() = false

}
