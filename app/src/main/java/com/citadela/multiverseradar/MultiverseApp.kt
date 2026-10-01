package com.citadela.multiverseradar

import android.app.Application
import com.citadela.multiverseradar.data.local.AppDatabase
import com.citadela.multiverseradar.data.remote.RickAndMortyApi
import com.citadela.multiverseradar.data.repository.CharacterRepository

/** Injeção de dependências manual: cria as peças uma única vez. */
class MultiverseApp : Application() {

    val repository: CharacterRepository by lazy {
        CharacterRepository(
            api = RickAndMortyApi.create(),
            dao = AppDatabase.getInstance(this).characterDao()
        )
    }
}
