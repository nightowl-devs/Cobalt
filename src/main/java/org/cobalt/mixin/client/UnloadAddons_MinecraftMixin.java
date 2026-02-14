package org.cobalt.mixin.client;

import kotlin.Pair;
import net.minecraft.client.Minecraft;
import org.cobalt.api.addon.Addon;
import org.cobalt.api.addon.AddonMetadata;
import org.cobalt.internal.loader.AddonLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(Minecraft.class)
public class UnloadAddons_MinecraftMixin {

  @Inject(method = "close", at = @At("HEAD"))
  public void onClose(CallbackInfo callbackInfo) {
    List<Pair<AddonMetadata, Addon>> addonsList = AddonLoader.INSTANCE.getAddons();

    addonsList.forEach((addon) -> {
      addon.getSecond().onUnload();
    });
  }

}
