package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseMainSlider(
    val `data`: MainSliderData,
    val status: Int
)

data class MainSliderData(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val key: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val value: Value
)

data class Value(
    val slides: List<Slide>
)

data class Slide(
    @SerializedName("btn_text")
    val btnText: String,
    var description: String,
    @SerializedName("img_src")
    var imgSrc: String,
    val index: String,
    @SerializedName("is_show")
    val isShow: String,
    val title: String,
    val url: String
)