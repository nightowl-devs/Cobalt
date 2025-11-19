package org.cobalt.internal.command

import org.cobalt.api.command.Command
import org.cobalt.api.command.annotation.DefaultHandler
import org.cobalt.api.command.annotation.SubCommand
import org.cobalt.api.util.ChatUtils
import org.cobalt.internal.feat.general.NameProtect
import org.cobalt.internal.ui.screen.ConfigScreen

object MainCommand : Command(
  name = "cobalt",
  aliases = arrayOf("cb")
) {

  @DefaultHandler
  fun main() {
    ConfigScreen.openUI()
  }

  @SubCommand
  fun dev(subCmd: String) {
    when (subCmd) {
      "tnp" -> NameProtect.isEnabled = !NameProtect.isEnabled
      "tchat" -> {
        ChatUtils.sendMessage("This is a test message!")
        ChatUtils.sendDebug("This is a test debug message!")
      }
    }
  }

}
