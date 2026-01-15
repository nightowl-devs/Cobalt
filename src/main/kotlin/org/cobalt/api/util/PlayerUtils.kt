package org.cobalt.api.util

import kotlin.math.ceil
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.util.math.BlockPos

object PlayerUtils {

  private val mc: MinecraftClient =
    MinecraftClient.getInstance()

  /**
   * @return The player's current position
   */
  @JvmStatic
  val position: BlockPos?
    get() = mc.player?.position()

  /**
   * @return The player's current FOV
   */
  @JvmStatic
  val fov: Int
    get() = mc.options.fov.value

  /**
   * @return The current position of a ClientPlayerEntity
   */
  @JvmStatic
  fun ClientPlayerEntity.position(): BlockPos {
    return BlockPos.ofFloored(x, ceil(y - 0.25), z)
  }

}
