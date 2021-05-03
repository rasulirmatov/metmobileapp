package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ResponseClassesBySubject(
    val `data`: List<Classes>,
    val status: String
)

data class Classes(
    @SerializedName("class")
    val classX: String,
    val id: Int,
    @SerializedName("subject_id")
    val subjectId: Int
)