package com.example.authorlistdata.di

import android.content.Context
import androidx.room.Room
import com.example.authorlistdata.Repo.MessageRepository
import com.example.authorlistdata.api.ApiService
import com.example.mvvmretrofitcoroutines.data.AuthorDao
import com.example.mvvmretrofitcoroutines.data.AuthorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context):AuthorDatabase =
        Room.databaseBuilder(context,AuthorDatabase::class.java,"postDatabase").allowMainThreadQueries()
            .build()

    @Provides
    fun providesPostDao(postDatabase: AuthorDatabase):AuthorDao =
        postDatabase.appDao()

    @Provides
    fun providesPostRepository(authorDao: AuthorDao,apiService: ApiService):MessageRepository =
        MessageRepository(authorDao,apiService)


    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient().newBuilder().addNetworkInterceptor(loggingInterceptor).build()
    }
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://message-list.appspot.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideRetrofitService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
}