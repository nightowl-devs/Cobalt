package org.cobalt.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MouseClickAccessor_MinecraftClientMixin {

  @Invoker("doAttack")
  boolean leftClick();

  @Invoker("doItemUse")
  void rightClick();

}
