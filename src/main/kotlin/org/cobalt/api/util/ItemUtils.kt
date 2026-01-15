package org.cobalt.api.util

import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.text.Text

fun ItemStack.getLoreLines(): List<Text> {
  val lore = this.get(DataComponentTypes.LORE) ?: return emptyList()
  return lore.lines()
}
