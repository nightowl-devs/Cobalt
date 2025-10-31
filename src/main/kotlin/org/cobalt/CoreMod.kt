package org.cobalt

import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import org.cobalt.util.helper.TickScheduler

object CoreMod : ClientModInitializer{

  val mc: MinecraftClient
    get() = MinecraftClient.getInstance()

  @Suppress("UNUSED_EXPRESSION")
  override fun onInitializeClient() {
    TickScheduler

    println("Cobalt Mod Initialized")
  }

}
