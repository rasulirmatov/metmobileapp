package com.app.maktabielektroni.models.response


import com.google.gson.annotations.SerializedName

data class ResponseClusters(
    val `data`: List<ClusterItem>
)

data class ClusterItem(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    val index: Int,
    val name: String,
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)