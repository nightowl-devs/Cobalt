package org.cobalt.internal.ui.components

import java.awt.Color
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import org.cobalt.api.notification.NotificationManager
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.internal.ui.UIComponent
import org.cobalt.internal.ui.animation.ColorAnimation
import org.cobalt.internal.ui.util.isHoveringOver

internal class UIUpdateButton : UIComponent(
    x = 0F,
    y = 0F,
    width = 100F,
    height = 30F
) {
    private val colorAnim = ColorAnimation(150L)
    private var wasHovering = false

    override fun render() {
        val hovering = isHoveringOver(x, y, width, height)

        if (hovering != wasHovering) {
            colorAnim.start()
            wasHovering = hovering
        }

        val bgColor = colorAnim.get(
            Color(42, 42, 42, 50),
            Color(61, 94, 149, 50),
            !hovering
        )

        val borderColor = colorAnim.get(
            Color(42, 42, 42),
            Color(61, 94, 149),
            !hovering
        )

        val textColor = colorAnim.get(
            Color(230, 230, 230),
            Color(61, 94, 149),
            !hovering
        )

        NVGRenderer.rect(x, y, width, height, bgColor.rgb, 5F)
        NVGRenderer.hollowRect(x, y, width, height, 2F, borderColor.rgb, 5F)

        NVGRenderer.image(
            refreshIcon,
            x + width / 2F - 40F,
            y + height / 2F - 10F,
            20F,
            20F,
            0F,
            textColor.rgb
        )

        NVGRenderer.text(
            "Update",
            x + width / 2F - 2F,
            y + height / 2F - 4F,
            11F,
            textColor.rgb
        )
    }

    override fun mouseClicked(button: Int): Boolean {
        if (isHoveringOver(x, y, width, height) && button == 0) {
            NotificationManager.sendNotification("Update Started", "Downloading and installing update...", 3000)
        }
        return false
    }
    override fun mouseReleased(button: Int): Boolean = false
    override fun mouseDragged(button: Int, offsetX: Double, offsetY: Double): Boolean = false
    override fun charTyped(input: CharInput): Boolean = false
    override fun keyPressed(input: KeyInput): Boolean = false

    companion object {
        private val refreshIcon = NVGRenderer.createImage("/assets/cobalt/icons/refresh.svg")
    }
}
