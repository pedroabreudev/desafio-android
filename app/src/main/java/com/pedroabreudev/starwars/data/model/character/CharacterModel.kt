package com.pedroabreudev.starwars.data.model.character

import java.io.Serializable

data class CharacterModel(
    val name: String,
    val height: String,
    val gender: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,

    ) : Serializable
