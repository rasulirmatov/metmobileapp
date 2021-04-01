package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ClassItem(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("image_src")
    val imageSrc: Any,
    val name: String,
    val slug: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)