package org.cobalt.internal.ui.progress

import java.util.UUID
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.ChatScreen
import org.cobalt.api.event.annotation.SubscribeEvent
import org.cobalt.api.event.impl.render.NvgEvent
import org.cobalt.api.progress.ProgressAPI
import org.cobalt.api.progress.ProgressHandle
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.api.util.ui.helper.Image

object ProgressManager : ProgressAPI {

  private val mc: Minecraft = Minecraft.getInstance()
  private val progressBars = mutableMapOf<String, UIProgress>()
  private const val GAP = 5F
  private const val MARGIN = 15F

  override fun showProgress(progress: Int, color: Int, icon: Image?, position: ProgressPosition): ProgressHandle {
    val handle = UUID.randomUUID().toString()
    progressBars[handle] = UIProgress(handle, progress.coerceIn(0, 100), color, icon, position)
    return ProgressHandle(this, handle)
  }

  override fun updateProgress(handle: String, progress: Int) {
    progressBars[handle]?.updateProgress(progress)
  }

  override fun incrementProgress(handle: String, amount: Int) {
    progressBars[handle]?.incrementProgress(amount)
  }

  override fun decrementProgress(handle: String, amount: Int) {
    progressBars[handle]?.decrementProgress(amount)
  }

  override fun updateColor(handle: String, color: Int) {
    progressBars[handle]?.updateColor(color)
  }

  override fun removeProgress(handle: String) {
    progressBars[handle]?.startClosing()
  }

  override fun clear() {
    progressBars.values.forEach { it.startClosing() }
  }

  @Suppress("unused")
  @SubscribeEvent
  fun onRender(event: NvgEvent) {
    val window = mc.window
    val screenWidth = window.screenWidth.toFloat()
    val screenHeight = window.screenHeight.toFloat()

    NVGRenderer.beginFrame(screenWidth, screenHeight)

    val toRemove = mutableListOf<String>()
    val shouldRender = if (mc.screen != null) {
      mc.screen is ChatScreen
    } else {
      true
    }
    if (shouldRender) {
      val grouped = progressBars.entries
        .filter { !it.value.shouldRemove() }
        .groupBy { it.value.position }

      grouped.forEach { (position, barEntries) ->
        renderPositionBars(position, barEntries.map { it.value }, screenWidth, screenHeight)
      }
    }

    progressBars.forEach { (handle, progress) ->
      if (progress.shouldRemove()) {
        toRemove.add(handle)
      }
    }

    progressBars.keys.removeAll(toRemove)
    NVGRenderer.endFrame()
  }

  private fun renderPositionBars(position: ProgressPosition, bars: List<UIProgress>, screenWidth: Float, screenHeight: Float) {
    var yOffset = 0F

    bars.forEach { bar ->
      val (x, y) = calculatePosition(position, bar, screenWidth, screenHeight, yOffset)
      bar.updateBounds(x, y)
      bar.render()
      yOffset += bar.height + GAP
    }
  }

  private fun calculatePosition(position: ProgressPosition, bar: UIProgress, screenWidth: Float, screenHeight: Float, offset: Float): Pair<Float, Float> {
    return when (position) {
      ProgressPosition.TOP_LEFT -> Pair(MARGIN, MARGIN + offset)
      ProgressPosition.TOP_CENTER -> Pair((screenWidth - bar.width) / 2, MARGIN + offset)
      ProgressPosition.TOP_RIGHT -> Pair(screenWidth - bar.width - MARGIN, MARGIN + offset)

      ProgressPosition.CENTER_LEFT -> Pair(MARGIN, (screenHeight - bar.height) / 2 + offset)
      ProgressPosition.CENTER -> Pair((screenWidth - bar.width) / 2, (screenHeight - bar.height) / 2 + offset)
      ProgressPosition.CENTER_RIGHT -> Pair(screenWidth - bar.width - MARGIN, (screenHeight - bar.height) / 2 + offset)

      ProgressPosition.BOTTOM_LEFT -> Pair(MARGIN, screenHeight - bar.height - MARGIN - offset)
      ProgressPosition.BOTTOM_CENTER -> Pair((screenWidth - bar.width) / 2, screenHeight - bar.height - MARGIN - offset)
      ProgressPosition.BOTTOM_RIGHT -> Pair(screenWidth - bar.width - MARGIN, screenHeight - bar.height - MARGIN - offset)
    }
  }

}
