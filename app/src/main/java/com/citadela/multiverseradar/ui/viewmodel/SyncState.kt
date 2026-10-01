package com.citadela.multiverseradar.ui.viewmodel

/** Estados possíveis da sincronização com a API. */
sealed interface SyncState {
    data object Idle : SyncState
    data object Loading : SyncState
    data class Success(val count: Int) : SyncState
    data class Error(val message: String) : SyncState
}
