package dev.project.serinochallenge.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.project.serinochallenge.data.local.dao.SerinoDao

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideSerinoDao(itunesDatabase: SerinoDatabase) : SerinoDao {
        return itunesDatabase.serinoDao()
    }

    @Provides
    fun provideItunesDatabase(@ApplicationContext appContext: Context) :
            SerinoDatabase {
        return Room.databaseBuilder(
            appContext,
            SerinoDatabase::class.java,
            "SerinoDB.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    }
}