package com.example.authorlistdata.api

import com.example.authorlistdata.MessageData
import retrofit2.Response
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private var apiService: ApiService) {
   suspend fun getAuthorDetails(): Response< MessageData> = apiService.getMessage("5","Cj4KEAoKbWVzc2FnZV9pZBICCAoSJmoOc35tZXNzYWdlLWxpc3RyFAsSB01lc3NhZ2UYgICAgOvJkQoMGAAgAA")
}