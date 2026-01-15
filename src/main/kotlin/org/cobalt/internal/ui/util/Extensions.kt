package org.cobalt.internal.ui.util

import net.minecraft.client.MinecraftClient

inline val mouseX: Double
  get() = MinecraftClient.getInstance().mouse.x

inline val mouseY: Double
  get() = MinecraftClient.getInstance().mouse.y

fun isHoveringOver(x: Float, y: Float, width: Float, height: Float): Boolean =
  mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height
