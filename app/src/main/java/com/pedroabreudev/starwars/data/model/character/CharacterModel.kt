package com.pedroabreudev.starwars.data.model.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "characterModel")
data class CharacterModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val height: String,
    val gender: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,

    ) : Serializable
