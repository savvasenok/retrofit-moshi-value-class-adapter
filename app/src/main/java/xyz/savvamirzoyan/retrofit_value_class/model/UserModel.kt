package xyz.savvamirzoyan.retrofit_value_class.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class UserModel(

    @Json(name = "id")
    val id: @RawValue UserId,

    @Json(name = "name")
    val name: String,

    @Json(name = "username")
    val username: String,

    @Json(name = "email")
    val email: @RawValue Email,
) : Parcelable, Serializable

@JvmInline
value class UserId(val id: Int) {

    override fun toString(): String {
        return "id: $id"
    }
}

@JvmInline
value class Email(val email: String) {

    fun domain() = email.split("@").last()
    fun content() = email.split("@").first()
}
