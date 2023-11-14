package dev.project.serinochallenge.data.remote

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.project.serinochallenge.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModules {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context) =
        Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache
    ) =
        OkHttpClient.Builder().apply {
            cache(cache)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            connectTimeout(OKHTTP_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(OKHTTP_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(OKHTTP_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL + "/")
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideItunesService(retrofit: Retrofit): SerinoService =
        retrofit.create(SerinoService::class.java)

    private const val OKHTTP_CONNECT_TIMEOUT_SECONDS = 15L
    private const val OKHTTP_READ_TIMEOUT_SECONDS = 15L
    private const val OKHTTP_WRITE_TIMEOUT_SECONDS = 15L
    private const val CACHE_SIZE = (10L * 1024L * 1024L) // 10MB
}

