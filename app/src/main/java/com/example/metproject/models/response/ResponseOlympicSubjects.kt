package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ResponseOlympicSubjects(
    val `data`: List<OlympiadSubject>,
    val status: Int
)

data class OlympiadSubject(
    @SerializedName("class_id")
    val classId: Int,
    @SerializedName("class_name")
    val className: Int,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("subject_name")
    val subjectName: String
)