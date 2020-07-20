package com.example.recyclerviewex

import com.example.mycoroutinessample.data.model.User
import com.google.gson.JsonObject
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface ApiService {


    @FormUrlEncoded
    @POST("/posts/insert.php")
    suspend fun createUser(@FieldMap body: HashMap<String, String>): User


    @GET("/posts/show.php")
    suspend fun getUsers(): List<User>

    // Update single note
    @FormUrlEncoded
    @POST("/posts/update.php")
    suspend fun updateUser(@FieldMap body: HashMap<String, String>): User

    // Delete note
    @FormUrlEncoded
    @POST("/posts/delet.php")
    suspend fun deleteUser(@FieldMap body: HashMap<String, String>): JsonObject



}