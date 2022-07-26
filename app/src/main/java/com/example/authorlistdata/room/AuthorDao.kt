package com.example.mvvmretrofitcoroutines.data

import androidx.room.*


@Dao
interface AuthorDao {

    @Query("SELECT * FROM author WHERE id ")
     fun getAll(): List<Authorentity>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(authorlist: List<Authorentity>)

    @Update
     fun update(author: Authorentity)

    @Delete
     fun delete(author: Authorentity)

}