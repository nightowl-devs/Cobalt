package org.cobalt.mixin.client;

import net.minecraft.client.Minecraft;
import org.cobalt.api.event.impl.client.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class TickEvent_MinecraftMixin {

  @Inject(at = @At("HEAD"), method = "tick")
  private void onStartTick(CallbackInfo callbackInfo) {
    TickEvent.Start startTickEvent = new TickEvent.Start();
    startTickEvent.post();
  }

  @Inject(at = @At("RETURN"), method = "tick")
  private void onEndTick(CallbackInfo callbackInfo) {
    TickEvent.End endTickEvent = new TickEvent.End();
    endTickEvent.post();
  }

}
