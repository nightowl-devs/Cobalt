package org.cobalt.internal.command

import java.awt.Color
import kotlin.random.Random
import org.cobalt.api.command.Command
import org.cobalt.api.command.annotation.DefaultHandler
import org.cobalt.api.command.annotation.SubCommand
import org.cobalt.api.notification.NotificationManager
import org.cobalt.api.pathfinder.PathExecutor
import org.cobalt.api.progress.ProgressHandle
import org.cobalt.api.rotation.EasingType
import org.cobalt.api.rotation.RotationExecutor
import org.cobalt.api.rotation.strategy.TimedEaseStrategy
import org.cobalt.api.util.helper.Rotation
import org.cobalt.api.util.ui.NVGRenderer
import org.cobalt.api.util.ui.helper.Image
import org.cobalt.internal.ui.progress.ProgressManager
import org.cobalt.internal.ui.progress.ProgressPosition
import org.cobalt.internal.ui.screen.UIConfig

internal object MainCommand : Command(name = "cobalt", aliases = arrayOf("cb")) {

  private var autoProgressHandles = mutableListOf<ProgressHandle>()

  @DefaultHandler
  fun main() {
    UIConfig.openUI()
  }

  @SubCommand
  fun rotate(yaw: Double, pitch: Double, duration: Int) {
    RotationExecutor.rotateTo(
      Rotation(yaw.toFloat(), pitch.toFloat()),
      TimedEaseStrategy(
        yawEasing = EasingType.EASE_OUT_EXPO,
        pitchEasing = EasingType.EASE_OUT_EXPO,
        duration = duration.toLong()
      )
    )
  }

  @SubCommand
  fun rotate() {
    val yaw = Random.nextFloat() * 360f - 180f
    val pitch = Random.nextFloat() * 180f - 90f

    RotationExecutor.rotateTo(
      Rotation(yaw, pitch),
      TimedEaseStrategy(
        yawEasing = EasingType.EASE_OUT_EXPO,
        pitchEasing = EasingType.EASE_OUT_EXPO,
        duration = 400L
      )
    )
  }

  @SubCommand
  fun start(x: Double, y: Double, z: Double) {
    PathExecutor.start(x, y, z)
  }

  @SubCommand
  fun stop() {
    PathExecutor.stop()
  }

  @SubCommand
  fun notification(title: String, description: String) {
    NotificationManager.sendNotification(title, description)
  }

  @SubCommand
  fun progress(progress: Int) {
    val image =  NVGRenderer.createImage("/assets/cobalt/steve.png")
    ProgressManager.showProgress(progress, Color(33, 150, 243).rgb, icon = image, position = ProgressPosition.CENTER)
  }

  @SubCommand
  fun increment(amount: Int = 1) {
    if (autoProgressHandles.isEmpty()) {
      NotificationManager.sendNotification("Progress", "No auto progress bars")
      return
    }
    autoProgressHandles.forEach { it.increment(amount) }
  }

  @SubCommand
  fun decrement(amount: Int = 1) {
    if (autoProgressHandles.isEmpty()) {
      NotificationManager.sendNotification("Progress", "No auto progress bars")
      return
    }
    autoProgressHandles.forEach { it.decrement(amount) }
  }

  @SubCommand
  fun progressauto(count: Int = 3) {
    ProgressManager.clear()
    autoProgressHandles.clear()

    val colors = listOf(
      Color(244, 67, 54).rgb,
      Color(33, 150, 243).rgb,
      Color(76, 175, 80).rgb,
      Color(255, 193, 7).rgb,
      Color(156, 39, 176).rgb,
    )

    val positions = listOf(
      ProgressPosition.TOP_LEFT, ProgressPosition.TOP_CENTER, ProgressPosition.TOP_RIGHT,
      ProgressPosition.CENTER_LEFT, ProgressPosition.CENTER, ProgressPosition.CENTER_RIGHT,
      ProgressPosition.BOTTOM_LEFT, ProgressPosition.BOTTOM_CENTER, ProgressPosition.BOTTOM_RIGHT,
    )

    repeat(count) { index ->
      val image =  NVGRenderer.createImage("/assets/cobalt/steve.png")
      val handle = ProgressManager.showProgress(
        progress = 0,
        color = colors[index % colors.size],
        icon = image,
        position = positions[index % positions.size]
      )
      autoProgressHandles.add(handle)
    }
  }

  @SubCommand
  fun progresshide() {
    ProgressManager.clear()
    autoProgressHandles.clear()
    NotificationManager.sendNotification("Progress", "All hidden")
  }

}
