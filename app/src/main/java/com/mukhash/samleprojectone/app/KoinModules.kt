package com.mukhash.samleprojectone.app

import com.mukhash.samleprojectone.TranModule
import org.koin.core.module.Module

object KoinModules {
    val modules: List<Module> =
        listOf(
            TranModule.create()
        )
}