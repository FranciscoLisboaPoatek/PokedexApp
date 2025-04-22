package com.example.pokedexapp.domain.models

// TODO make this a val from the sealed class
enum class SpriteType(val isShiny: Boolean) {
    FRONT_DEFAULT(false),
    FRONT_SHINY_DEFAULT(true),
    BACK_DEFAULT(false),
    BACK_SHINY_DEFAULT(true),
}

sealed class PokemonSprite(val spriteType: SpriteType, val spriteUrl: String?) {
    class FrontDefaultSprite(spriteUrl: String?) :
        PokemonSprite(SpriteType.FRONT_DEFAULT, spriteUrl)

    class FrontShinySprite(spriteUrl: String?) :
        PokemonSprite(SpriteType.FRONT_SHINY_DEFAULT, spriteUrl)

    class BackDefaultSprite(spriteUrl: String?) : PokemonSprite(SpriteType.BACK_DEFAULT, spriteUrl)

    class BackShinySprite(spriteUrl: String?) :
        PokemonSprite(SpriteType.BACK_SHINY_DEFAULT, spriteUrl)
}
