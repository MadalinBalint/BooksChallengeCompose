package com.mendelin.bookschallege.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.mendelin.bookschallege.data.remote.BooksApi
import com.mendelin.bookschallege.data.repository.BooksRepository
import com.mendelin.bookschallege.domain.GetBooksUseCase
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
    fun provideGson(): Gson = GsonBuilder().setStrictness(Strictness.LENIENT)
        .setPrettyPrinting()
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pastebin.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideBooksApi(retrofit: Retrofit): BooksApi =
        retrofit.create(BooksApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: BooksApi) =
        BooksRepository(api)

    @Provides
    @Singleton
    fun provideUseCase(repository: BooksRepository) =
        GetBooksUseCase(repository)
}
