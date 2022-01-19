package com.example.hw6architecture.Ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


fun <T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    observer: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { value ->
                observer(value)
            }
    }
}