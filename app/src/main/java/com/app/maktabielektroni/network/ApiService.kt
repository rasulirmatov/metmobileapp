package com.app.maktabielektroni.network

import com.app.maktabielektroni.models.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("classes")
    fun getClasses(): Call<ResponseClassesModel>
    @GET("subjects")
    fun getSubjects(): Call<ResponseSubjectsModel>
    @GET("classesBySubject?")
    fun getClassesBySubject(@Query("subject_id") subject_id: String): Call<ResponseClassesBySubject>
    @GET("subjectsByClass?")
    fun getSubjectsByClass(@Query("sinf_id") class_id: String): Call<ResponseSubjectsByClass>
    @GET("themes?")
    fun getThemes(@Query("sinf_id") class_id: String,@Query("subject_id") subject_id: String): Call<ResponseThemesModel>
    @GET("theme?")
    fun getThemeDetail(@Query("theme_id") theme_id: String): Call<ResponseThemeDetail>
    @GET("news")
    fun getNews(): Call<ResponseNews>
    @GET("main_slider")
    fun getMainSlider(): Call<ResponseMainSlider>
    @GET("second_slider")
    fun getSecondSlider(): Call<ResponseSecondSlider>
    @GET("olympicClasses")
    fun getOlympicClasses(): Call<ResponseOlympicClasses>
    @GET("olympicSubjects?")
    fun getOlympicSubjects(@Query("sinf_id") class_id: String): Call<ResponseOlympicSubjects>
    @GET("olympics?")
    fun getOlympicDetail(@Query("sinf_id") class_id: String,@Query("subject_id") subject_id: String): Call<ResponseOlympicDetail>
    @GET("clusters")
    fun getClasters(): Call<ResponseClusters>
    @GET("cluster?")
    fun getSubjectsByClusterId(@Query("cluster_id") cluster_id: String): Call<ResponseSubjectsByClusterId>
    @GET("mmt_fan?")
    fun get_mmt_fan(@Query("mmt_fan_id") mmt_fan_id: String): Call<ResponseMMTdetail>

}