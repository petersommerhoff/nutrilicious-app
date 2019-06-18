package com.petersommerhoff.nutrilicious.data.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * @author Peter Sommerhoff
 */
val NETWORK = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

val networkScope = CoroutineScope(NETWORK)