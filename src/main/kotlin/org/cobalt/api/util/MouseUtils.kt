package org.cobalt.api.util

import net.minecraft.client.MinecraftClient
import org.cobalt.mixin.client.MouseClickAccessor_MinecraftClientMixin

object MouseUtils {

  private val mc: MinecraftClient =
    MinecraftClient.getInstance()

  @JvmStatic
  fun leftClick() {
    (mc as MouseClickAccessor_MinecraftClientMixin).leftClick()
  }

  @JvmStatic
  fun rightClick() {
    (mc as MouseClickAccessor_MinecraftClientMixin).rightClick()
  }

}
