package com.pedroabreudev.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pedroabreudev.starwars.data.model.character.CharacterModel

@Database(entities = [CharacterModel::class], version = 1, exportSchema = false)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract fun starwarsDao(): StarWarsDao
}