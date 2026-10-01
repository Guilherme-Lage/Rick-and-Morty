# 🪐 Multiverse Radar

App Android (Jetpack Compose) **Offline-First** que cataloga personagens de Rick and Morty.

## Como abrir
1. Android Studio (Ladybug ou mais novo) → **File > Open** → selecione a pasta `MultiverseRadar`.
2. Aguarde o Gradle Sync (se pedir para gerar o *Gradle Wrapper*, aceite).
3. Rode no emulador (API 24+).

## Arquitetura

```
API (Retrofit) ──syncCharacters()──► Room (SSOT) ──Flow──► Repository ──StateFlow──► ViewModel ──► Compose UI
```

| Camada | Arquivo | O que faz |
|---|---|---|
| Rede | `data/remote/CharacterDto.kt` | `data class` que espelham o JSON (campos anuláveis = desserialização segura) |
| Rede | `data/remote/RickAndMortyApi.kt` | Interface Retrofit com `suspend fun getCharacters()` + Gson |
| Banco | `data/local/CharacterEntity.kt` | `@Entity` (id, name, species, status, imageUrl) |
| Banco | `data/local/CharacterDao.kt` | `Flow<List<CharacterEntity>>` + `@Upsert suspend` em lote |
| Banco | `data/local/AppDatabase.kt` | `@Database` singleton |
| Mapper | `data/mapper/CharacterMapper.kt` | Conversão DTO → Entity, descartando registros inválidos |
| SSOT | `data/repository/CharacterRepository.kt` | Expõe o Flow do Room e `suspend fun syncCharacters()` |
| Estado | `ui/viewmodel/SyncState.kt` | `sealed interface`: Idle, Loading, Success, Error |
| VM | `ui/viewmodel/CharacterViewModel.kt` | `StateFlow` da lista + do estado; trata IOException/HttpException |
| UI | `ui/screen/CharacterScreen.kt` | TopAppBar + Refresh, CircularProgressIndicator, Snackbar, LazyColumn de Cards com Coil |

**Regra de Ouro:** a ViewModel só conhece o Repository; a UI só conhece a ViewModel. Ninguém acima do Repository toca na API.

## Roteiro dos testes em sala

**Teste do Modo Avião**
1. Abra o app com internet → a lista carrega (snackbar "✅ 20 personagens sincronizados").
2. Ative o Modo Avião, feche o app (remova dos recentes) e abra de novo.
3. A lista continua visível, pois vem do Room. Aparece só um aviso de que não há conexão.

**Resiliência a falhas**
1. Com Modo Avião ligado, toque em ⟳.
2. Aparece a Snackbar "Sem conexão com o multiverso 📡…".
3. Nenhum item some: o `@Upsert` só roda se a API responder, e ele nunca apaga dados.
