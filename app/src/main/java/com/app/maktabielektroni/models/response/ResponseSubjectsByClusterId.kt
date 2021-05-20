package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseSubjectsByClusterId(
    val `data`: MMTSubjectsData,
    val status: String
)

data class MMTSubjectsData(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val index: Int,
    val mmts: List<Mmt>,
    val name: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class Mmt(
    @SerializedName("cluster_id")
    val clusterId: Int,
    val component: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("mmt_fan_id")
    val mmtFanId: Int,
    val status: Int,
    val subject: SubjectItem,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class SubjectItem(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("image_src")
    val imageSrc: String,
    val name: String,
    val slug: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)