package com.example.hw7.view.viewModels

import android.media.metrics.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw7.model.core.Either
import com.example.hw7.model.models.ImageResponse
import com.example.hw7.model.repositories.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val imageRepository: ImageRepository) : ViewModel() {

    private val _images = MutableLiveData<Either<Throwable, ImageResponse>>()
    val images: LiveData<Either<Throwable, ImageResponse>> get() = _images

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get()= _event

    sealed class Event {
        data class ShowError(val image: String) : Event()
    }

    fun getImages(apiKey: String, query: String) {
        viewModelScope.launch {
            when(val response = imageRepository.getImages(apiKey, query)){
                is Either.Success -> _images.postValue(response)
                is Either.Error -> _event.postValue(Event.ShowError("${response.error.message}"))
            }
        }
    }

//    private fun sendEvent(event: Event) {
//        _event.postValue(event)
//    }
}