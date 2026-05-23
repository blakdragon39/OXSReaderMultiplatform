package com.blakdragon.oxsreadermultiplatform

import com.blakdragon.oxsreadermultiplatform.core.PatternUiReducer
import org.koin.dsl.module

val appModule = module {
    factory<PatternUiReducer> { PatternUiReducer() }
}