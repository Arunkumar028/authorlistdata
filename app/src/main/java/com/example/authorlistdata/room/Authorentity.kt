package com.example.mvvmretrofitcoroutines.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "author")
data class Authorentity(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "updated")
    val updated: String?,
    @ColumnInfo(name = "photoUrl")
    val photoUrl: String?,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo (name = "favorite")
    val favoriteicon:String
):Serializable

