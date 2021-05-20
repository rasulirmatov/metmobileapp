package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseSubjectsModel(
    val `data`: List<SubjectsItem>,
    val status: String
)

data class SubjectsItem(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("image_src")
    val imageSrc: String,
    val name: String,
    val slug: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)