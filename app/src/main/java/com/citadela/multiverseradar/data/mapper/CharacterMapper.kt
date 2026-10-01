package com.citadela.multiverseradar.data.mapper

import com.citadela.multiverseradar.data.local.CharacterEntity
import com.citadela.multiverseradar.data.remote.CharacterDto

/**
 * Conversão segura Rede -> Banco.
 * - Sem id ou sem nome, o registro é descartado (retorna null).
 * - Campos opcionais ausentes ganham valores padrão.
 * Assim nenhum dado "quebrado" da API chega ao Room.
 */
fun CharacterDto.toEntityOrNull(): CharacterEntity? {
    val safeId = id ?: return null
    val safeName = name?.takeIf { it.isNotBlank() } ?: return null

    return CharacterEntity(
        id = safeId,
        name = safeName,
        species = species?.takeIf { it.isNotBlank() } ?: "Desconhecida",
        status = status?.takeIf { it.isNotBlank() } ?: "unknown",
        imageUrl = image.orEmpty()
    )
}

fun List<CharacterDto>.toEntities(): List<CharacterEntity> = mapNotNull { it.toEntityOrNull() }
