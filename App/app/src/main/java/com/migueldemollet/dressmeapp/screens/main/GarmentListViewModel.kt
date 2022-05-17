package com.migueldemollet.dressmeapp.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.migueldemollet.dressmeapp.repositories.GarmentRepository
import com.migueldemollet.dressmeapp.repositories.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GarmentListViewModel
@Inject
constructor(private val garmentRepository: GarmentRepository) : ViewModel() {
    private val _state: MutableState<GarmentListState> = mutableStateOf(GarmentListState())
    val state: State<GarmentListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init { getGarmentList() }

    fun getGarmentList() {
        garmentRepository.getGarmentList().onEach { result ->
            when(result) {
                is Result.Error -> _state.value = GarmentListState(error = result.message ?: "")
                is Result.Loading -> _state.value = GarmentListState(isLoading = true)
                is Result.Success -> _state.value = GarmentListState(garments = result.data ?: emptyList())
            }
        }.launchIn(viewModelScope)
    }
}