package com.example.pkoandroidchallenge.di

import com.example.pkoandroidchallenge.data.MoviesApi
import com.example.pkoandroidchallenge.util.Constants.API_KEY
import com.example.pkoandroidchallenge.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer $API_KEY")
                        .build()
                )
            }
            .build()

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): MoviesApi =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
}
