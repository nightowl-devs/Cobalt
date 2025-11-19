package org.cobalt.api.module.setting.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.cobalt.api.module.setting.Setting
import org.lwjgl.glfw.GLFW

class ColorSetting(
  name: String,
  description: String,
  subcategory: String,
  defaultValue: Int
) : Setting<Int>(name, description, subcategory, defaultValue) {

  override fun read(element: JsonElement) {
    this.value = element.asInt
  }

  override fun write(): JsonElement {
    return JsonPrimitive(value)
  }

}
