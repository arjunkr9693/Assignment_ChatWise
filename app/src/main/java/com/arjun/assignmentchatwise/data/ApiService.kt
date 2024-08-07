package com.arjun.assignmentchatwise.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiInterface::class.java)

    suspend fun getProducts(): List<Product> {
        return api.getProducts().products
    }

}

interface ApiInterface {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}
