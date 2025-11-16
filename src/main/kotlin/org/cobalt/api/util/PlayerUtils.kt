package org.cobalt.api.util

import kotlin.math.ceil
import net.minecraft.util.math.BlockPos
import org.cobalt.Cobalt.mc

object PlayerUtils {
 /**
  * @return The player's current position
  */
  val position: BlockPos
    get() = BlockPos.ofFloored(
      mc.player!!.x,
      ceil(mc.player!!.y) - 1,
      mc.player!!.z
    )
 /**
  * @return The player's current FOV
  */
  val fov: Int
    get() = mc.options.fov.value

}
