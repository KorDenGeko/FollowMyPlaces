package com.example.followmyplaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel:ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState
    private val repo = MyApplication.getApp().repo
    fun getSimpleRoute() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getSimpleRoute()
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        _uiState.postValue(
                            UIState.ResultDirections(response.body()!!))
                    }
                } else
                    _uiState.postValue(UIState.Error(response.message()))
            }
        }
    }


    fun getNearbyPlaces() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getNearbyPlaces()
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        _uiState.postValue(
                            UIState.Result(response.body()!!))
                    }
                } else
                    _uiState.postValue(UIState.Error(response.message()))
            }
        }
    }

    fun getComplexRoute(originId: String,destinationId: String,waypoints: String, key: String) {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repo.getComplexRoute(originId,destinationId,waypoints, key)
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        _uiState.postValue(
                            UIState.ResultDirections(response.body()!!))
                    }
                } else
                    _uiState.postValue(UIState.Error(response.message()))
            }
        }
    }

    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class ResultDirections(val directionsResponse:DirectionsResponse) : UIState()
        class Result(val placesResponse:PlacesResponse) : UIState()
        class Error(val description: String) : UIState()
    }
}
