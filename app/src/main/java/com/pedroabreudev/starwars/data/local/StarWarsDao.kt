package com.pedroabreudev.starwars.data.local

import androidx.room.*
import com.pedroabreudev.starwars.data.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow

@Dao
interface StarWarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterModel: CharacterModel): Long

    @Query("SELECT * FROM characterModel ORDER BY id")
    fun getAll(): Flow<List<CharacterModel>>

    @Delete
    suspend fun delete(characterModel: CharacterModel)
}