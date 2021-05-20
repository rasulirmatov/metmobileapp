package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseSubjectsByClass(
    val `data`: List<Subjects>,
    val status: String
)

data class Subjects(
    val id: Int,
    val name: String,
    @SerializedName("sinf_id")
    val sinfId: Int,
    val slug: String
)