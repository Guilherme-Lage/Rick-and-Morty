package com.citadela.multiverseradar.data.repository

import com.citadela.multiverseradar.data.local.CharacterDao
import com.citadela.multiverseradar.data.local.CharacterEntity
import com.citadela.multiverseradar.data.mapper.toEntities
import com.citadela.multiverseradar.data.remote.RickAndMortyApi
import kotlinx.coroutines.flow.Flow

/**
 * Single Source of Truth (SSOT).
 *
 * - LEITURA: sempre do Room (characters). A UI nunca lê a API.
 * - ESCRITA: syncCharacters() busca na API e grava no Room.
 *   O Flow do Room emite automaticamente a lista atualizada.
 *
 * Se a rede falhar, a exceção sobe para a ViewModel e NADA é apagado
 * do banco — os personagens já salvos continuam na tela.
 */
class CharacterRepository(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {

    val characters: Flow<List<CharacterEntity>> = dao.observeAll()

    suspend fun syncCharacters(page: Int = 1): Int {
        val response = api.getCharacters(page)          // pode lançar IOException
        val entities = response.results.orEmpty().toEntities()
        dao.upsertAll(entities)                         // upsert: nunca apaga
        return entities.size
    }
}
