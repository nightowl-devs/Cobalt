package org.cobalt.internal.helper

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import net.minecraft.client.MinecraftClient
import org.cobalt.internal.loader.AddonLoader

internal object Config {

  private val mc: MinecraftClient = MinecraftClient.getInstance()
  private val gson = GsonBuilder().setPrettyPrinting().create()
  private val modulesFile = File(mc.runDirectory, "config/cobalt/addons.json")

  fun loadModulesConfig() {
    if (!modulesFile.exists()) {
      modulesFile.parentFile?.mkdirs()
      modulesFile.createNewFile()
      return
    }

    val text = modulesFile.bufferedReader().use { it.readText() }
    if (text.isEmpty()) return

    val addonsMap = AddonLoader.getAddons().associate { it.first.id to it.second }

    runCatching {
      JsonParser.parseString(text).asJsonArray
    }.getOrNull()?.forEach { element ->
      val addonObj = element.asJsonObject
      val addonId = addonObj.get("addon").asString
      val addon = addonsMap[addonId] ?: return@forEach

      val modulesMap = addon.getModules().associateBy { it.name }
      val settingsMap = modulesMap.values.flatMap { it.getSettings() }.associateBy { it.name }

      addonObj.getAsJsonArray("modules")?.forEach { moduleElement ->
        val moduleObj = moduleElement.asJsonObject
        val moduleName = moduleObj.get("name").asString
        modulesMap[moduleName] ?: return@forEach

        moduleObj.getAsJsonObject("settings")?.entrySet()?.forEach { (key, value) ->
          settingsMap[key]?.read(value)
        }
      }
    }
  }

  fun saveModulesConfig() {
    val jsonArray = JsonArray()

    AddonLoader.getAddons().forEach { (metadata, addon) ->
      val addonObject = JsonObject()
      addonObject.addProperty("addon", metadata.id)

      val modulesArray = JsonArray()
      addon.getModules().forEach { module ->
        val moduleObject = JsonObject()
        moduleObject.addProperty("name", module.name)

        val settingsObject = JsonObject()
        module.getSettings().forEach { setting ->
          settingsObject.add(setting.name, setting.write())
        }
        moduleObject.add("settings", settingsObject)
        modulesArray.add(moduleObject)
      }

      addonObject.add("modules", modulesArray)
      jsonArray.add(addonObject)
    }

    modulesFile.bufferedWriter().use { it.write(gson.toJson(jsonArray)) }
  }
}
