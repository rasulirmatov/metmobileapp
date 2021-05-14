package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ResponseNews(
    val `data`: List<NewsItem>,
    val status: String
)

data class NewsItem(
    @SerializedName("created_at")
    val createdAt: String,
    val description: String,
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    val status: Int,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)