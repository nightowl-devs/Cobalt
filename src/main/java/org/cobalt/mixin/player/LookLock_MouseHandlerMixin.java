package org.cobalt.mixin.player;

import net.minecraft.client.MouseHandler;
import org.cobalt.api.util.MouseUtils;
import org.cobalt.api.util.player.MovementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MouseHandler.class)
public abstract class LookLock_MouseHandlerMixin {

  @Shadow
  private boolean mouseGrabbed;

  @Shadow
  public abstract void releaseMouse();

  @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
  private void onUpdateMouse(CallbackInfo callbackInfo) {
    if (MovementManager.isLookLocked) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "isMouseGrabbed", at = @At("HEAD"), cancellable = true)
  private void onIsCursorLocked(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
    if (MouseUtils.isMouseUngrabbed()) {
      if (this.mouseGrabbed) {
        this.releaseMouse();
      }

      callbackInfoReturnable.setReturnValue(false);
    }
  }

  @Inject(method = "grabMouse", at = @At("HEAD"), cancellable = true)
  private void onLockCursor(CallbackInfo callbackInfo) {
    if (MouseUtils.isMouseUngrabbed()) {
      callbackInfo.cancel();
    }
  }

}
