package com.example.authorlistdata

import com.google.gson.annotations.SerializedName

data class MessageData(
    @SerializedName("count")
    val count: String,
    @SerializedName("pageToken")
    val pagetoken: String,
    @SerializedName("messages")
    val message: List<Message>
)

data class Message(
    @SerializedName("content")
    val content: String,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("author")
    var auther: Author
)

data class Author(
    @SerializedName("name")
    val name: String,
    @SerializedName("photoUrl")
    val photos: String
)