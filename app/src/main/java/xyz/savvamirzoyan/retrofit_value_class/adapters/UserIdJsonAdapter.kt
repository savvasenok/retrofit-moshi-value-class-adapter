package xyz.savvamirzoyan.retrofit_value_class.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import xyz.savvamirzoyan.retrofit_value_class.model.UserId

class UserIdJsonAdapter(moshi: Moshi) : JsonAdapter<UserId>() {

    private val intAdapter = moshi.adapter(Int::class.java)

    override fun fromJson(reader: JsonReader): UserId {
        val integer = reader.nextInt()
        return UserId(integer)
    }

    override fun toJson(writer: JsonWriter, value: UserId?) {
        intAdapter.toJson(writer, value?.id)
    }
}
