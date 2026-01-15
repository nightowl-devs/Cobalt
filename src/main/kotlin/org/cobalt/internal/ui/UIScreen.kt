package org.cobalt.internal.ui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.cobalt.api.util.TickScheduler

internal abstract class UIScreen : Screen(Text.empty()) {

  protected val mc: MinecraftClient =
    MinecraftClient.getInstance()

  fun openUI() =
    TickScheduler.schedule(1) { mc.setScreen(this) }

  override fun shouldPause() = false

}
