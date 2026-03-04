package org.cobalt.internal.ui.progress

import java.awt.Color
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.api.util.ui.helper.Image
import org.cobalt.internal.ui.UIComponent
import org.cobalt.internal.ui.animation.EaseOutAnimation

internal class UIProgress(
  private val handle: String,
  private var progress: Int,
  private var color: Int,
  private val icon: Image? = null,
  val position: ProgressPosition = ProgressPosition.TOP_CENTER,
) : UIComponent(
  x = 0F,
  y = 0F,
  width = 340F,
  height = 35F
) {

  private var createdAt = System.currentTimeMillis()
  private var completedAt: Long? = null
  private val slideAnim = EaseOutAnimation(150L)
  private val progressAnim = EaseOutAnimation(300L)
  private var isClosing = false
  private val barWidth = 250F
  private val barHeight = 8F
  private val containerPadding = 12F
  private val iconSize = 24F
  private val iconMargin = 10F
  private var displayProgress = 0
  private var animationProgress = 0F

  init {
    slideAnim.start()
  }

  fun updateProgress(newProgress: Int) {
    val capped = newProgress.coerceIn(0, 100)
    displayProgress = progress
    this.progress = capped
    progressAnim.start()
    if (capped >= 100) {
      completedAt = System.currentTimeMillis()
    }
  }

  fun incrementProgress(amount: Int) {
    val newVal = (progress + amount).coerceIn(0, 100)
    displayProgress = progress
    this.progress = newVal
    progressAnim.start()
    if (newVal >= 100) {
      completedAt = System.currentTimeMillis()
    }
  }

  fun decrementProgress(amount: Int) {
    this.progress = (progress - amount).coerceIn(0, 100)
  }

  fun updateColor(newColor: Int) {
    this.color = newColor
  }

  fun startClosing() {
    if (!isClosing) {
      isClosing = true
      slideAnim.start()
    }
  }

  fun shouldRemove(): Boolean {
    if (completedAt != null) {
      val elapsed = System.currentTimeMillis() - completedAt!!
      if (elapsed > 1500L) {
        if (!isClosing) {
          isClosing = true
          slideAnim.start()
        }
      }
    }

    val elapsed = System.currentTimeMillis() - createdAt
    return elapsed > 150L && isClosing && !slideAnim.isAnimating()
  }

  private fun getOffsetX(): Float {
    return if (isClosing) {
      slideAnim.get(0F, width)
    } else {
      width - slideAnim.get(0F, width)
    }
  }

  fun getHandle(): String = handle

  override fun render() {
    val offsetX = getOffsetX()
    val finalX = x + offsetX

    val containerX = finalX + containerPadding
    val containerY = y + (height - barHeight) / 2
    val containerWidth = iconSize + iconMargin + barWidth + containerPadding * 2
    val containerHeight = barHeight + containerPadding * 2

    NVGRenderer.enableBlur(5F)
    NVGRenderer.rect(
      containerX - containerPadding,
      containerY - containerPadding,
      containerWidth,
      containerHeight,
      Color(40, 40, 40, 140).rgb,
      12F
    )

    NVGRenderer.disableBlur()

    if (icon != null) {
      NVGRenderer.image(
        icon,
        containerX + iconMargin / 2 - 4F,
        containerY + (containerHeight - iconSize) / 2 - 12F,
        iconSize,
        iconSize
      )
    }

    val barX = containerX + iconSize + iconMargin
    NVGRenderer.rect(
      barX,
      containerY,
      barWidth,
      barHeight,
      Color(30, 30, 30, 120).rgb,
      4F
    )

    val animatedProgress = progressAnim.get(displayProgress.toFloat(), progress.toFloat(), false).toInt()
    val filledWidth = (barWidth * animatedProgress.coerceAtMost(100)) / 100F
    if (filledWidth > 0F) {
      NVGRenderer.rect(
        barX,
        containerY,
        filledWidth,
        barHeight,
        color,
        4F
      )
    }
  }

}
