package org.cobalt.api.module

object ModuleManager {

  private val moduleList = mutableListOf<Module>()

  fun addModules(vararg modules: Module) {
    moduleList.addAll(listOf(*modules))
  }

  fun getModules(): List<Module> {
    return moduleList
  }

  fun getCategories(): List<String> {
    return moduleList
      .map { it.category }
      .distinct()
      .sortedBy { it }
  }

  fun clearModules() {
    moduleList.clear()
  }

  infix fun removeModule(module: Module) {
    moduleList.remove(module)
  }

}
