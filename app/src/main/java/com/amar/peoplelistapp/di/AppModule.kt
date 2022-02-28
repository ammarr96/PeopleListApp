package com.amar.peoplelistapp.di

import android.content.Context
import androidx.room.Room
import com.amar.peoplelistapp.api.ApiService
import com.amar.peoplelistapp.db.LikeDAO
import com.amar.peoplelistapp.db.PeopleDatabase
import com.amar.peoplelistapp.repository.PeopleRepository
import com.amar.peoplelistapp.util.Constants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    public fun provideApiService() : ApiService {

        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(240, TimeUnit.SECONDS)
        httpClient.connectTimeout(240, TimeUnit.SECONDS)
        httpClient.writeTimeout(240, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PeopleDatabase::class.java, "people.db")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun providePersonDao(database: PeopleDatabase) =
        database.likeDao()


    @Singleton
    @Provides
    public fun providePeopleRepository(peopleDAO: LikeDAO, apiService: ApiService) : PeopleRepository {
        return PeopleRepository(peopleDAO, apiService)
    }

}