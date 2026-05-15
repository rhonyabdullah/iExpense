package com.mobile.iexpense.core.component.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

fun Modifier.clearFocusOnTap(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    remember(focusManager) {
        Modifier.pointerInput(focusManager) {
            detectTapGestures(onTap = {
                focusManager.clearFocus(force = true)
            })
        }
    }
}
