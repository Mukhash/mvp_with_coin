package kz.azatserzhanov.common.base

import org.koin.core.module.Module

interface InjectionModule {
    fun create(): Module
}