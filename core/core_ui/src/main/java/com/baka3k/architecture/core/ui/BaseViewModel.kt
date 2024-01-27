package com.baka3k.architecture.core.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

}
fun <T> StateFlow<T>.collectsEvents(
    lifecycleScope: CoroutineScope,
    viewLifecycleOwner: LifecycleOwner,
    listenState: Lifecycle.State = Lifecycle.State.CREATED,
    collector: FlowCollector<T>
) {
    val stateFlow = this
    lifecycleScope.launch {
        if (listenState == Lifecycle.State.INITIALIZED) {
            stateFlow.collect(collector)
        } else {
            viewLifecycleOwner.repeatOnLifecycle(listenState) {
                stateFlow.collect(collector)
            }
        }
    }
}