package com.petersommerhoff.nutrilicious.data.network

import kotlinx.coroutines.experimental.newFixedThreadPoolContext

/**
 * @author Peter Sommerhoff
 */
val NETWORK = newFixedThreadPoolContext(2, "NETWORK")