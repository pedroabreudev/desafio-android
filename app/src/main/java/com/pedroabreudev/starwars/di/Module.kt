package com.pedroabreudev.starwars.di

import android.content.Context
import androidx.room.Room
import com.pedroabreudev.starwars.data.local.StarWarsDatabase
import com.pedroabreudev.starwars.data.remote.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    const val BASE_URL = "https://swapi.dev/api/"

    @Singleton
    @Provides
    fun provideStarWarsDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        StarWarsDatabase::class.java,
        "sw.db"
    ).build()

    @Singleton
    @Provides
    fun provideStarWarsDao(database: StarWarsDatabase) = database.starwarsDao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }
}