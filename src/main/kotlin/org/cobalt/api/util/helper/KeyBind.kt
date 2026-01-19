package org.cobalt.api.util.helper

import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.InputUtil

class KeyBind(
  var keyCode: Int = -1,
) {

  private var wasPressed = false

  fun isPressed(): Boolean {
    if (keyCode == -1) return false
    val mc = MinecraftClient.getInstance()

    val isPressed = mc.currentScreen == null
      && InputUtil.isKeyPressed(mc.window, keyCode)

    return (isPressed && !wasPressed).also {
      wasPressed = isPressed
    }
  }

}
