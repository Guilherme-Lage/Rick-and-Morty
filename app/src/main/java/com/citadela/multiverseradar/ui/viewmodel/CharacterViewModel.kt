package com.citadela.multiverseradar.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.citadela.multiverseradar.MultiverseApp
import com.citadela.multiverseradar.data.local.CharacterEntity
import com.citadela.multiverseradar.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    /** Lista vinda EXCLUSIVAMENTE do Room (via Repository). */
    val characters: StateFlow<List<CharacterEntity>> = repository.characters
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    init {
        // Tenta atualizar ao abrir. Offline? Mostra o aviso e mantém o cache.
        syncCharacters()
    }

    fun syncCharacters() {
        if (_syncState.value is SyncState.Loading) return

        viewModelScope.launch {
            _syncState.value = SyncState.Loading
            _syncState.value = try {
                SyncState.Success(repository.syncCharacters())
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                SyncState.Error("Sem conexão com o multiverso 📡 Mostrando os personagens salvos.")
            } catch (e: HttpException) {
                SyncState.Error("A Cidadela respondeu com erro ${e.code()}. Tente novamente.")
            } catch (e: Exception) {
                SyncState.Error("Falha inesperada na sincronização: ${e.localizedMessage ?: "erro desconhecido"}")
            }
        }
    }

    /** Chamado pela UI depois de exibir a Snackbar. */
    fun onMessageShown() {
        _syncState.value = SyncState.Idle
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MultiverseApp
                CharacterViewModel(app.repository)
            }
        }
    }
}
