package com.pedroabreudev.starwars.data.model.character

import java.io.Serializable

data class CharacterModelData(
    val results: List<CharacterModel> = listOf()
) : Serializable
