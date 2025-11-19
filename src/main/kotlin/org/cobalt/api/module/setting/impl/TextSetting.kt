package org.cobalt.api.module.setting.impl

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import org.cobalt.api.module.setting.Setting

class TextSetting(
  name: String,
  description: String,
  subcategory: String,
  defaultValue: String
) : Setting<String>(name, description, subcategory, defaultValue) {

  override fun read(element: JsonElement) {
    this.value = element.asString
  }

  override fun write(): JsonElement {
    return JsonPrimitive(value)
  }

}
