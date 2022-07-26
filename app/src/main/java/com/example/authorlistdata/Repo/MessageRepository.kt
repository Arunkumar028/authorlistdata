package com.example.authorlistdata.Repo

import com.example.authorlistdata.MessageData
import com.example.authorlistdata.api.ApiService
import com.example.mvvmretrofitcoroutines.data.AuthorDao
import com.example.mvvmretrofitcoroutines.data.Authorentity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val authorDao: AuthorDao,
    private val apiService: ApiService
) {
    val getAllPost: List<Authorentity> = authorDao.getAll()

    suspend fun insert(postList: List<Authorentity>) = withContext(Dispatchers.IO) {
        authorDao.insertAll(postList)
    }

    suspend fun getAuthorDetails() : Response <MessageData> {
        return  apiService.getMessage("5","Cj4KEAoKbWVzc2FnZV9pZBICCAoSJmoOc35tZXNzYWdlLWxpc3RyFAsSB01lc3NhZ2UYgICAgOvJkQoMGAAgAA")
    }

    suspend fun deleted(authorentity: Authorentity) = withContext(Dispatchers.IO) {
        authorDao.delete(authorentity)
    }

    suspend fun update(authorentity: Authorentity) = withContext(Dispatchers.IO) {
        authorDao.update(authorentity)
    }
//    fun getData(): Flow<Response<Message>> = flow {
//        val response = apiServiceImpl.getAuthorDetails()
//        emit(response)
//    }.flowOn(Dispatchers.IO)
}