package com.example.coronatracker.dI

import android.content.Context
import androidx.room.Room
import com.example.coronatracker.api.Methods
import com.example.coronatracker.dataClasses.Constants.BASE_COUNTRY_URL
import com.example.coronatracker.dataClasses.Constants.COUNTRY
import com.example.coronatracker.dataClasses.Constants.COUNTRY_API
import com.example.coronatracker.dataClasses.Constants.INDIA
import com.example.coronatracker.dataClasses.Constants.INDIA_API
import com.example.coronatracker.dataClasses.Constants.INDIA_URL
import com.example.coronatracker.dataClasses.Constants.WORLD
import com.example.coronatracker.dataClasses.Constants.WORLD_API
import com.example.coronatracker.dataClasses.Constants.WORLD_URL
import com.example.coronatracker.room.TrackDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named(INDIA)
    fun provideIndiaRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(INDIA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @Named(WORLD)
    fun provideWorldRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WORLD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @Named(COUNTRY)
    fun provideCountryRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_COUNTRY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named(COUNTRY_API)
    fun provideCountryApi(
        @Named(COUNTRY) retrofit: Retrofit
    ): Methods =
        retrofit.create(Methods::class.java)

    @Provides
    @Singleton
    @Named(WORLD_API)
    fun provideWorldApi(
        @Named(WORLD) retrofit: Retrofit
    ): Methods =
        retrofit.create(Methods::class.java)

    @Provides
    @Singleton
    @Named(INDIA_API)
    fun provideIndiaApi(
        @Named(INDIA) retrofit: Retrofit
    ): Methods =
        retrofit.create(Methods::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context.applicationContext,
            TrackDatabase::class.java,
            "contact.database"
        ).build()
}