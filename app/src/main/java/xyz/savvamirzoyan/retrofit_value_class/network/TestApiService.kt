package xyz.savvamirzoyan.retrofit_value_class.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import xyz.savvamirzoyan.retrofit_value_class.model.UserModel

interface TestApiService {

    @GET("/users")
    suspend fun getUsers(): List<UserModel>

    @POST("/posts")
    suspend fun send(@Body user: UserModel)
}
