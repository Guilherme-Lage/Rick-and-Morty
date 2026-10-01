package com.citadela.multiverseradar.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    /** Fluxo reativo: emite de novo sempre que a tabela mudar. */
    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun observeAll(): Flow<List<CharacterEntity>>

    /** Sincronização em lote: insere novos e atualiza os existentes (pelo id). */
    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)
}
