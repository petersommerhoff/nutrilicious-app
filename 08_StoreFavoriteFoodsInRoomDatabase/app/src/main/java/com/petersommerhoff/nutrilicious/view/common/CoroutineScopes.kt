package com.petersommerhoff.nutrilicious.view.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val UI = Dispatchers.Main + SupervisorJob()
val uiScope = CoroutineScope(UI)

val BACKGROUND = Dispatchers.Default
val bgScope = CoroutineScope(BACKGROUND)