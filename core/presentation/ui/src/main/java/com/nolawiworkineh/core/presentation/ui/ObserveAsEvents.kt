package com.nolawiworkineh.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


@Composable
// helper function for one time events from view model to ui
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        // Ensure the `Flow` is only collected when the `LifecycleOwner` is at least in the `STARTED` state.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Switch the coroutine context to the main thread immediately to perform UI-related actions.
            withContext(Dispatchers.Main.immediate) {
                // Collect values from the `Flow` and pass each emitted value to the `onEvent` lambda.
                flow.collect(onEvent)
            }
        }
    }
}
