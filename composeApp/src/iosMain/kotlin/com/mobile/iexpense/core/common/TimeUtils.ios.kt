package com.mobile.iexpense.core.common

import platform.posix.time

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual fun currentTimeMillis(): Long = (time(null) * 1000L)
