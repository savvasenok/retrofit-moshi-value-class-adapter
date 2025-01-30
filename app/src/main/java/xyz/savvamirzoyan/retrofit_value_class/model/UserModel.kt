package xyz.savvamirzoyan.retrofit_value_class.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class UserModel(

    @Json(name = "id") val id: UserId,

    @Json(name = "name") val name: String,

    @Json(name = "username") val username: String,

    @Json(name = "email") val email: Email,
) : Parcelable, Serializable

@Parcelize
@JvmInline
value class UserId(val id: Int) : Parcelable, Serializable {

    override fun toString(): String {
        return "id: $id"
    }
}

@Parcelize
@JvmInline
value class Email(val email: String) : Parcelable, Serializable {

    fun domain() = email.split("@").last()
    fun content() = email.split("@").first()
}
