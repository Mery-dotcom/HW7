package com.example.hw7.view.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw7.model.core.Either
import com.example.hw7.model.models.ImageResponse
import com.example.hw7.model.models.WeatherResponse
import com.example.hw7.model.repositories.ImageRepository
import com.example.hw7.model.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _images = MutableLiveData<ImageResponse>()
    val images: LiveData<ImageResponse> get() = _images

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> get() = _weather

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get()= _event

    sealed class Event {
        data class ShowError(val image: String) : Event()
    }

    fun getWeather(apiKey: String, query: String) {
        viewModelScope.launch {
            when(val response = weatherRepository.getWeather(apiKey, query)){
                is Either.Success -> _weather.postValue(response.success)
                is Either.Error -> _event.postValue(Event.ShowError("${response.error.message}"))
            }
        }
    }

    fun getImages(apiKey: String, query: String) {
        viewModelScope.launch {
            when(val response = imageRepository.getImages(apiKey, query)){
                is Either.Success -> _images.postValue(response.success)
                is Either.Error -> _event.postValue(Event.ShowError("${response.error.message}"))
            }
        }
    }
}