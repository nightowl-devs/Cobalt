package org.cobalt.api.util

import net.minecraft.client.MinecraftClient
import net.minecraft.screen.slot.SlotActionType

object InventoryUtils {

  private val mc: MinecraftClient =
    MinecraftClient.getInstance()

  private val player
    get() = mc.player

  private val interactionManager
    get() = mc.interactionManager

  @JvmStatic
  fun clickSlot(
    slot: Int,
    click: ClickType = ClickType.LEFT,
    action: SlotActionType = SlotActionType.PICKUP,
  ) {
    val player = player ?: return
    val handler = player.currentScreenHandler

    interactionManager?.clickSlot(
      handler.syncId,
      slot,
      click.ordinal,
      action,
      player
    )
  }

  @JvmStatic
  fun holdHotbarSlot(slot: Int) {
    if (slot !in 0..8) return
    player?.inventory?.selectedSlot = slot
  }

  @JvmStatic
  fun findItemInHotbar(name: String): Int {
    val player = player ?: return -1
    val inventory = player.inventory

    for (i in 0..8) {
      val stack = inventory.getStack(i)
      if (stack.isEmpty) continue

      val displayName =
        stack.name.string

      if (displayName.contains(name, ignoreCase = true)) {
        return i
      }
    }

    return -1
  }

  @JvmStatic
  fun findItemInHotbarWithLore(lore: String): Int {
    val player = player ?: return -1
    val inventory = player.inventory

    for (i in 0..8) {
      val stack = inventory.getStack(i)
      if (stack.isEmpty) continue

      for (line in stack.getLoreLines()) {
        if (line.string.contains(lore, ignoreCase = true)) {
          return i
        }
      }
    }

    return -1
  }

  @JvmStatic
  fun findItemInInventory(name: String): Int {
    val player = player ?: return -1
    val inventory = player.inventory

    for (i in 0 until inventory.size()) {
      val stack = inventory.getStack(i)
      if (stack.isEmpty) continue

      if (stack.name.string.contains(name, ignoreCase = true)) {
        return i
      }
    }

    return -1
  }

  @JvmStatic
  fun findItemInInventoryWithLore(lore: String): Int {
    val player = player ?: return -1
    val inventory = player.inventory

    for (i in 0 until inventory.size()) {
      val stack = inventory.getStack(i)
      if (stack.isEmpty) continue

      for (line in stack.getLoreLines()) {
        if (line.string.contains(lore, ignoreCase = true)) {
          return i
        }
      }
    }

    return -1
  }

}

enum class ClickType {
  LEFT,
  RIGHT,
  MIDDLE
}
