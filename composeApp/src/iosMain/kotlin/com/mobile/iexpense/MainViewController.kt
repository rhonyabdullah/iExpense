package com.mobile.iexpense

import androidx.compose.ui.window.ComposeUIViewController
import com.mobile.iexpense.di.initKoin

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    },
    content = { App() }
)
