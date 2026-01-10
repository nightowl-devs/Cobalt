package org.cobalt.internal.ui.components.settings

import java.awt.Color
import net.minecraft.client.MinecraftClient
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import org.cobalt.api.module.setting.impl.TextSetting
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.internal.ui.UIComponent
import org.cobalt.internal.ui.util.TextInputHandler
import org.cobalt.internal.ui.util.isHoveringOver
import org.cobalt.internal.ui.util.mouseX
import org.lwjgl.glfw.GLFW

internal class UITextSetting(private val setting: TextSetting) : UIComponent(
  x = 0F,
  y = 0F,
  width = 627.5F,
  height = 60F,
) {

  private val inputHandler = TextInputHandler(setting.value)
  private var focused = false
  private var dragging = false

  override fun render() {
    NVGRenderer.rect(x, y, width, height, Color(42, 42, 42, 50).rgb, 10F)
    NVGRenderer.hollowRect(x, y, width, height, 1F, Color(42, 42, 42).rgb, 10F)
    NVGRenderer.text(setting.name, x + 20F, y + 14.5F, 15F, Color(230, 230, 230).rgb)
    NVGRenderer.text(setting.description, x + 20F, y + 32F, 12F, Color(179, 179, 179).rgb)

    val inputX = x + width - 280F
    val inputY = y + 15F
    val borderColor = if (focused) Color(61, 94, 149).rgb else Color(42, 42, 42).rgb

    NVGRenderer.rect(inputX, inputY, 300F, 30F, Color(42, 42, 42, 50).rgb, 5F)
    NVGRenderer.hollowRect(inputX, inputY, 300F, 30F, 2F, borderColor, 5F)

    val textX = inputX + 10F
    val textY = inputY + 9F

    if (focused) inputHandler.updateScroll(280F, 13F)

    NVGRenderer.pushScissor(inputX + 10F, inputY, 280F, 30F)

    if (focused) {
      inputHandler.renderSelection(textX, textY, 13F, 13F, Color(70, 130, 180, 100).rgb)
    }

    NVGRenderer.text(inputHandler.getText(), textX - inputHandler.getTextOffset(), textY, 13F, Color(230, 230, 230).rgb)

    if (focused) {
      inputHandler.renderCursor(textX, textY, 13F, Color(230, 230, 230).rgb)
    }

    NVGRenderer.popScissor()
  }

  override fun mouseClicked(button: Int): Boolean {
    if (button != 0) return false

    val inputX = x + width - 280F
    val inputY = y + 15F

    if (isHoveringOver(inputX, inputY, 300F, 30F)) {
      focused = true
      dragging = true
      inputHandler.startSelection(mouseX.toFloat(), inputX + 10F, 13F)
      return true
    }

    if (focused) {
      setting.value = inputHandler.getText()
      focused = false
      return true
    }

    return false
  }

  override fun mouseReleased(button: Int): Boolean {
    if (button == 0) dragging = false
    return false
  }

  override fun mouseDragged(button: Int, offsetX: Double, offsetY: Double): Boolean {
    if (button == 0 && dragging && focused) {
      val inputX = x + width - 280F
      inputHandler.updateSelection(mouseX.toFloat(), inputX + 10F, 13F)
      return true
    }
    return false
  }

  override fun charTyped(input: CharInput): Boolean {
    if (!focused) return false

    val char = input.codepoint.toChar()
    if (char.code >= 32 && char != '\u007f') {
      inputHandler.insertText(char.toString())
      return true
    }

    return false
  }

  override fun keyPressed(input: KeyInput): Boolean {
    if (!focused) return false

    val ctrl = input.modifiers and GLFW.GLFW_MOD_CONTROL != 0
    val shift = input.modifiers and GLFW.GLFW_MOD_SHIFT != 0

    when (input.key) {
      GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_KEY_ENTER -> {
        setting.value = inputHandler.getText()
        focused = false
        return true
      }
      GLFW.GLFW_KEY_BACKSPACE -> { inputHandler.backspace(); return true }
      GLFW.GLFW_KEY_DELETE -> { inputHandler.delete(); return true }
      GLFW.GLFW_KEY_LEFT -> { inputHandler.moveCursorLeft(shift); return true }
      GLFW.GLFW_KEY_RIGHT -> { inputHandler.moveCursorRight(shift); return true }
      GLFW.GLFW_KEY_HOME -> { inputHandler.moveCursorToStart(shift); return true }
      GLFW.GLFW_KEY_END -> { inputHandler.moveCursorToEnd(shift); return true }
      GLFW.GLFW_KEY_A -> if (ctrl) { inputHandler.selectAll(); return true }
      GLFW.GLFW_KEY_C -> if (ctrl) {
        inputHandler.copy()?.let { MinecraftClient.getInstance().keyboard.clipboard = it }
        return true
      }
      GLFW.GLFW_KEY_X -> if (ctrl) {
        inputHandler.cut()?.let { MinecraftClient.getInstance().keyboard.clipboard = it }
        return true
      }
      GLFW.GLFW_KEY_V -> if (ctrl) {
        val clipboard = MinecraftClient.getInstance().keyboard.clipboard
        if (clipboard.isNotEmpty()) inputHandler.insertText(clipboard)
        return true
      }
    }

    return false
  }

}
