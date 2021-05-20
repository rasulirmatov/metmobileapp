package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseOlympicDetail(
    val `data`: OlympicDetail,
    val status: String
)

data class OlympicDetail(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: Any,
    val introduction: String,
    @SerializedName("is_show")
    val isShow: Int,
    @SerializedName("pdf_src")
    val pdfSrc: String,
    @SerializedName("sinf_id")
    val sinfId: Int,
    val status: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    val test: Any,
    val title: String,
    val type: Any,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)