package com.fullcreative.randomcity.di

import android.content.Context
import androidx.room.Room
import com.fullcreative.randomcity.data.dao.CityDao
import com.fullcreative.randomcity.data.repository.CityRepositoryImpl
import com.fullcreative.randomcity.data.roomDb.CityDatabase
import com.fullcreative.randomcity.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideCityDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        CityDatabase::class.java,
        "city_db"
    ).build()

    @Provides
    fun provideCityDao(
        cityDb: CityDatabase
    ) = cityDb.CityDao()

    @Provides
    fun provideBookRepository(
        cityDao: CityDao
    ): CityRepository = CityRepositoryImpl(
        cityDao = cityDao
    )
}