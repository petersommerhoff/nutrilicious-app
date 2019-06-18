package com.petersommerhoff.nutrilicious.data.db

import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * @author Peter Sommerhoff
 */
val DB = Executors.newSingleThreadExecutor().asCoroutineDispatcher()