package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseThemeDetail(
    val `data`: ThemeDetail,
    val status: String
)

data class ThemeDetail(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val introduction: String,
    @SerializedName("is_show")
    val isShow: Int,
    val name: String,
    @SerializedName("pdf_exercise")
    val pdfExercise: String,
    @SerializedName("pdf_src")
    val pdfSrc: String,
    @SerializedName("plan_id")
    val planId: Int,
    val status: Int,
    val test: Test,
    @SerializedName("theme_num")
    val themeNum: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("video_src")
    val videoSrc: String,
    @SerializedName("view_count")
    val viewCount: Int
)

data class Test(
    val scripts: List<String>,
    val styles: List<String>,
    val tests: List<TestX>
)

data class TestX(
    val `data`: List<DataX>,
    val type: String
)

data class DataX(
    val answer: String,
    val answers: List<Answer>,
    val correctAnswer: String,
    val definitions: List<Definition>,
    val id: Int,
    val pairs: List<String>,
    val question: String,
    val terms: List<Term>,
    val text: String
)

data class Answer(
    val id: String,
    val text: String
)

data class Definition(
    val id: String,
    val text: String
)

data class Term(
    val id: String,
    val text: String
)

