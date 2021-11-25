package com.pedroabreudev.starwars.ui.list

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


class ListCharacterViewModel(
    private val repository: ServiceApi
) : ViewModel() {

    private val _list =
        MutableStateFlow<ResourceState<CharacterModelData>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<CharacterModelData>> = _list

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        safeFeatch()
    }

    private suspend fun safeFeatch() {
        try {
            val response = repository.getPeople()
            _list.value = handleResponse(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _list.value =
                    ResourceState.Error("Erro de conexão com a internet")
                else -> _list.value = ResourceState.Error("Falha de conversão de dados")
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