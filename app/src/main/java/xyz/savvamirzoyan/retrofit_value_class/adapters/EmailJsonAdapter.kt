package xyz.savvamirzoyan.retrofit_value_class.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import xyz.savvamirzoyan.retrofit_value_class.model.Email

class EmailJsonAdapter(
    moshi: Moshi
) : JsonAdapter<Email>() {

    private val stringAdapter = moshi.adapter(String::class.java)

    override fun fromJson(reader: JsonReader): Email {
        val string = reader.nextString()
        return Email(string)
    }

    override fun toJson(writer: JsonWriter, value: Email?) {
        stringAdapter.toJson(writer, value?.email)
    }
}
