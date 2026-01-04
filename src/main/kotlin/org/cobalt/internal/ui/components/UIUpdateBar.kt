package org.cobalt.internal.ui.components

import java.awt.Color
import net.minecraft.client.input.CharInput
import net.minecraft.client.input.KeyInput
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.internal.ui.UIComponent
import org.cobalt.internal.updater.UpdateData

internal class UIUpdateBar : UIComponent(
    x = 0F,
    y = 0F,
    width = 600F,
    height = 50F
) {
    private val updateButton = UIUpdateButton()
    private val padding = 12F

    override fun render() {
        val bgColor = Color(18, 18, 18).rgb
        val textColor = Color(230, 230, 230).rgb
        val secondaryTextColor = Color(179, 179, 179).rgb

        NVGRenderer.rect(
            x,
            y,
            width,
            height,
            bgColor,
            10F
        )

        NVGRenderer.text(
            "Update Available",
            x + padding,
            y + height / 2F - 8F,
            13F,
            textColor
        )

        NVGRenderer.text(
            "${UpdateData.currentVersion} → ${UpdateData.newVersion}",
            x + padding,
            y + height / 2F + 8F,
            11F,
            secondaryTextColor
        )

        updateButton.updateBounds(
            x + width - 100F - padding,
            y + (height - 30F) / 2F
        )
        updateButton.render()
    }

    fun getUpdateButton(): UIUpdateButton = updateButton

    override fun mouseClicked(button: Int): Boolean = false
    override fun mouseReleased(button: Int): Boolean = false
    override fun mouseDragged(button: Int, offsetX: Double, offsetY: Double): Boolean = false
    override fun charTyped(input: CharInput): Boolean = false
    override fun keyPressed(input: KeyInput): Boolean = false
}
