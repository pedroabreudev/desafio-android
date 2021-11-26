package com.pedroabreudev.starwars.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroabreudev.starwars.data.model.character.CharacterModelData
import com.pedroabreudev.starwars.data.remote.ServiceApi
import com.pedroabreudev.starwars.ui.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val repository: ServiceApi
) : ViewModel() {
    private val _searchCharacter =
        MutableStateFlow<ResourceState<CharacterModelData>>(ResourceState.Empty())
    val searchCharacter: StateFlow<ResourceState<CharacterModelData>> = _searchCharacter

    fun fetch(name: String) = viewModelScope.launch {
        safeFetch(name)
    }

    private suspend fun safeFetch(name: String) {
        _searchCharacter.value = ResourceState.Loading()
        try {
            val response = repository.getPeople(name)
            _searchCharacter.value = handleResponse(response)

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _searchCharacter.value = ResourceState.Error("Erro de rede")
                else -> _searchCharacter.value = ResourceState.Error("Erro de conversasão")
            }
        }
    }

    private fun handleResponse(response: Response<CharacterModelData>): ResourceState<CharacterModelData> {
        if (response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}