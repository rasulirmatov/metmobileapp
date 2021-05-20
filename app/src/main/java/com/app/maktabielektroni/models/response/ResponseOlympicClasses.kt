package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseOlympicClasses(
    val `data`: List<OlympiadClass>,
    val status: Int
)

data class OlympiadClass(
    val sinf: String,
    @SerializedName("sinf_id")
    val sinfId: Int
)