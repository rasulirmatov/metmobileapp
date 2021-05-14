package com.example.metproject.models.response


import com.google.gson.annotations.SerializedName

data class ResponseSecondSlider(
    val `data`: SecondSliderData,
    val status: Int
)

data class SecondSliderData(
    @SerializedName("created_at")
    val createdAt: Any,
    val id: Int,
    val key: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val value: ValueSecondSlider
)
data class ValueSecondSlider(
    @SerializedName("btn_text")
    val btnText: String,
    @SerializedName("btn_url")
    val btnUrl: String,
    val description: String,
    val slides: List<SlideItem>
)

data class SlideItem(
    @SerializedName("bg_color")
    val bgColor: String,
    @SerializedName("img_src")
    val imgSrc: String,
    val index: String,
    @SerializedName("is_show")
    val isShow: String,
    val title: String,
    val url: String
)