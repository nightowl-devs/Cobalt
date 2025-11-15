package org.cobalt.api.addon

abstract class Addon {

  // No need to edit these values, as they are set automatically on launch
  lateinit var name: String
  lateinit var description: String
  lateinit var version: String
  lateinit var authors: List<String>

  abstract fun onInitialize()
  abstract fun onUnload()

}
