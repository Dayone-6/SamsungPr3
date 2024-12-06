package ru.dayone.samsung3.features.proflie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.dayone.samsung3.features.proflie.domain.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    fun requestSavedAvatarUri(){
        viewModelScope.launch {
            val uri = profileRepository.getLocalImageUri()
            _data.postValue(uri)
        }
    }

    fun saveAvatarUri(uri: String){
        viewModelScope.launch {
            profileRepository.saveLocalAvatarUri(uri)
            _data.postValue(uri)
        }
    }
}