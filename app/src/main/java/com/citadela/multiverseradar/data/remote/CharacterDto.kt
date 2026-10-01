package com.citadela.multiverseradar.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Modelos de REDE: espelham exatamente o JSON da API.
 * Campos anuláveis = desserialização segura (a API pode omitir algo
 * e o Gson não quebra o app). A conversão para o banco fica no Mapper.
 */
data class CharacterResponseDto(
    @SerializedName("info") val info: InfoDto?,
    @SerializedName("results") val results: List<CharacterDto>?
)

data class InfoDto(
    @SerializedName("count") val count: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)

data class CharacterDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("image") val image: String?
)
