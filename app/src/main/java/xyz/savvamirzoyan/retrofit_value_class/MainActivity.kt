package xyz.savvamirzoyan.retrofit_value_class

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import xyz.savvamirzoyan.retrofit_value_class.adapters.ValueClassAdapterFactory
import xyz.savvamirzoyan.retrofit_value_class.model.Email
import xyz.savvamirzoyan.retrofit_value_class.model.UserId
import xyz.savvamirzoyan.retrofit_value_class.model.UserModel
import xyz.savvamirzoyan.retrofit_value_class.network.TestApiService
import xyz.savvamirzoyan.retrofit_value_class.ui.theme.RetrofitvalueclassTheme

class MainActivity : ComponentActivity() {

    private val serializerBuilder by lazy {
        Moshi.Builder()
            .add(ValueClassAdapterFactory())
            .build()
    }

    private val converterFactories by lazy {
        listOf(
            ScalarsConverterFactory.create(),
            MoshiConverterFactory.create(serializerBuilder),
        )
    }

    private val retrofit by lazy {
        val client = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build()

        Retrofit.Builder().client(client).apply { converterFactories.forEach { addConverterFactory(it) } }.baseUrl("https://jsonplaceholder.typicode.com").build()
    }

    private val service by lazy {
        retrofit.create(TestApiService::class.java)
    }

    private val users = MutableStateFlow<List<UserModel>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val usersList = users.collectAsState().value

            RetrofitvalueclassTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {

                        Row {
                            Button(onClick = ::makeRequest) {
                                Text("Click to get response")
                            }

                            Button(onClick = ::sendRequest) {
                                Text("Click to make POST")
                            }
                        }

                        HorizontalDivider()

                        usersList.forEach {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clickable {
                                        Toast
                                            .makeText(this@MainActivity, "Clicked: ${it.id}", Toast.LENGTH_SHORT)
                                            .show()
                                    }) {
                                Text(it.id.toString())

                                Spacer(Modifier.width(12.dp))

                                Column {
                                    Text(it.email.content())
                                    Text(it.email.domain())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun makeRequest() {
        lifecycleScope.launch {
            users.value = service.getUsers().shuffled().take(3)
        }
    }

    fun sendRequest() {
        lifecycleScope.launch {
            val id = UserId(123)
            val name = "John Doe"
            val username = "john.doe"
            val email = Email("john.doe@email.com")
            service.send(UserModel(id, name, username, email))
        }
    }
}
