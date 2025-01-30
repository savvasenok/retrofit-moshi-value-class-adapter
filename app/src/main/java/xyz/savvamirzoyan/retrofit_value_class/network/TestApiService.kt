package xyz.savvamirzoyan.retrofit_value_class.network

import retrofit2.http.GET
import xyz.savvamirzoyan.retrofit_value_class.model.UserModel

interface TestApiService {

    @GET("/users")
    suspend fun getUsers(): List<UserModel>
}
