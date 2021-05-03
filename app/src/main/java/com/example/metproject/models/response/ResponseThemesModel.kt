package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ResponseThemesModel(
    val `data`: Data,
    val status: String
)

data class Data(
    val book: Book,
    @SerializedName("book_id")
    val bookId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("is_show")
    val isShow: Int,
    val sinf: Sinf,
    @SerializedName("sinf_id")
    val sinfId: Int,
    val status: Int,
    val subject: Subject,
    @SerializedName("subject_id")
    val subjectId: Int,
    val themes: List<Themes>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)

data class Book(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("is_show")
    val isShow: Int,
    val name: String,
    @SerializedName("pdf_src")
    val pdfSrc: String,
    val slug: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)

data class Subject(
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

data class Sinf(
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

data class Themes(
    val id: Int,
    val introduction: String,
    @SerializedName("is_show")
    val isShow: Int,
    val name: String,
    @SerializedName("plan_id")
    val planId: Int,
    val status: Int,
    @SerializedName("theme_num")
    val themeNum: String
)

