package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseMMTdetail(
    val `data`: MMTData,
    val status: String
)

data class MMTData(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("is_show")
    val isShow: Int,
    @SerializedName("pdf_src")
    val pdfSrc: String,
    val status: Int,
    val test: TestMMT,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)

data class TestMMT(
    val scripts: List<String>,
    val styles: List<String>,
    val tests: List<TestXMMT>
)

data class TestXMMT(
    val `data`: List<DataXMMT>,
    val type: String
)

data class DataXMMT(
    val answer: String,
    val answers: List<AnswerMMT>,
    val correctAnswer: String,
    val id: String,
    val question: String,
    val text: String
)

data class AnswerMMT(
    val id: String,
    val text: String
)