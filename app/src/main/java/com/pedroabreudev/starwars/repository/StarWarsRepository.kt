package com.pedroabreudev.starwars.repository

import com.pedroabreudev.starwars.data.local.StarWarsDao
import com.pedroabreudev.starwars.data.model.character.CharacterModel
import javax.inject.Inject

class StarWarsRepository @Inject constructor(
    private val dao: StarWarsDao
) {
    suspend fun insert(characterModel: CharacterModel) = dao.insert(characterModel)
    fun getAll() = dao.getAll()
    suspend fun delete(characterModel: CharacterModel) = dao.delete(characterModel)

}