package xyz.savvamirzoyan.retrofit_value_class.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.rawType
import xyz.savvamirzoyan.retrofit_value_class.model.Email
import xyz.savvamirzoyan.retrofit_value_class.model.UserId
import java.lang.reflect.Type

class ValueClassAdapterFactory : JsonAdapter.Factory {

    override fun create(
        type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi
    ): JsonAdapter<*>? {
        if (type.rawType.kotlin.isValue) {
            if (type.rawType == UserId::class.java) {
                return UserIdJsonAdapter(moshi)
            }

            if (type.rawType == Email::class.java) {
                return EmailJsonAdapter(moshi)
            }
        }

        return null
    }
}
