package org.cobalt.internal.addon

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.metadata.ModMetadata
import org.cobalt.api.addon.Addon

object AddonManager {

  private val addons = mutableListOf<Addon>()

  fun loadAddons() {
    for (entrypoint in FabricLoader.getInstance().getEntrypointContainers("cobalt", Addon::class.java)) {
      val metadata: ModMetadata = entrypoint.provider.metadata
      var addon: Addon

      try {
        addon = entrypoint.getEntrypoint()
      } catch (ex: Exception) {
        throw Exception("Could not load addon: ${ex.message}")
      }

      addon.name = metadata.name
      addon.description = metadata.description
      addon.version = metadata.version.friendlyString
      addon.authors = metadata.authors.map {
        it.name
      }

      addon.onInitialize()
      addons.add(addon)
    }
  }

  fun getAddons(): List<Addon> {
    return addons
  }

}
