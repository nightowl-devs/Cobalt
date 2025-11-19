package org.cobalt.mixin.client;

import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public abstract class AddonList_CrashReportMixin {

  @Inject(method = "addDetails", at = @At("HEAD"))
  private void addAddonInfo(StringBuilder crashReportBuilder, CallbackInfo ci) {
    // TODO: Reimplement this when addons are a thing again
  }

}
