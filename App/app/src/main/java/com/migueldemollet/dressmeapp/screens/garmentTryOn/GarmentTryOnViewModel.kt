package com.migueldemollet.dressmeapp.screens.garmentTryOn

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.migueldemollet.dressmeapp.repositories.GarmentRepository
import com.migueldemollet.dressmeapp.repositories.Result
import com.migueldemollet.dressmeapp.screens.main.GarmentListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GarmentTryOnViewModel
@Inject
constructor(
    private val garmentRepository: GarmentRepository,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val _state: MutableState<GarmentTryOnState> = mutableStateOf(GarmentTryOnState())
    val state: State<GarmentTryOnState> = _state

    private val _state2: MutableState<GarmentListState> = mutableStateOf(GarmentListState())
    val state2: State<GarmentListState> = _state2

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        savedStateHandle.get<String>("garmentId")?.let { garmentId ->
            getGarment(garmentId)
        }
        savedStateHandle.get<String>("garmentId")?.let { garmentId ->
            savedStateHandle.get<String>("garmentColor")?.let { garmentColor ->
                savedStateHandle.get<String>("garmentType")?.let { garmentType ->
                    getGarmentListFilters(garmentId, garmentColor, garmentType)
                }
            }
        }
    }
    fun getGarment(id: String) {
        garmentRepository.getGarmentById(id).onEach { result ->
            when(result) {
                is Result.Error -> _state.value = GarmentTryOnState(error = result.message ?: "")
                is Result.Loading -> _state.value = GarmentTryOnState(isLoading = true)
                is Result.Success -> _state.value = GarmentTryOnState(garment = result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun getGarmentListFilters(id: String, color: String, type: String) {
        garmentRepository.getGarmentListByFilters(id, color, type).onEach { result ->
            when(result) {
                is Result.Error -> _state2.value = GarmentListState(error = result.message ?: "")
                is Result.Loading -> _state2.value = GarmentListState(isLoading = true)
                is Result.Success -> _state2.value = GarmentListState(garments = result.data ?: emptyList())
            }
        }.launchIn(viewModelScope)
    }
}