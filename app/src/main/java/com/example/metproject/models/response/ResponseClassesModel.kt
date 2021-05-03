package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ResponseClassesModel(
    val `data`: MutableList<ClassesItem>,
    val status: String
)
data class ClassesItem(
    @SerializedName("class")
    val classX: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val slug: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)