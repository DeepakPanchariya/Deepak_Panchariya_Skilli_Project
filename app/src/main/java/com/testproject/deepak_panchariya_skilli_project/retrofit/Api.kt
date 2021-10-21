package com.testproject.deepak_panchariya_skilli_project


import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface Api {

    @POST("b/60cb2fa78a4cd025b79f18c8")
    fun userGetData(@Body register:JsonObject):Call<JsonArray>




}