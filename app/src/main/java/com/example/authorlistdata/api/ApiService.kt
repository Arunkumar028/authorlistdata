package com.example.authorlistdata.api

import com.example.authorlistdata.MessageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

@GET("messages")
  suspend fun getMessage(
  @Query("count") count: String?,
  @Query("pageToken") pagetoken: String?,
  ): Response <MessageData>
}