package org.cobalt.mixin.client;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.cobalt.api.event.impl.client.MouseEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseEvent_MouseHandlerMixin {

  @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
  private void onMouseButton(long window, MouseButtonInfo input, int action, CallbackInfo ci) {
    MouseEvent event = cobalt$createMouseEvent(input.button(), action == 1);

    if (event != null && event.post()) {
      ci.cancel();
    }
  }

  @Unique
  private MouseEvent cobalt$createMouseEvent(int button, boolean isDown) {
    return switch (button) {
      case 0 -> isDown ? new MouseEvent.LeftClick(button) : new MouseEvent.LeftRelease(button);
      case 1 -> isDown ? new MouseEvent.RightClick(button) : new MouseEvent.RightRelease(button);
      case 2 -> isDown ? new MouseEvent.MiddleClick(button) : new MouseEvent.MiddleRelease(button);
      default -> null;
    };
  }

}
