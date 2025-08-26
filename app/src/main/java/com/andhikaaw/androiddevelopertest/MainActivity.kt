package com.andhikaaw.androiddevelopertest

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.andhikaaw.androiddevelopertest.data.local.AppDatabase
import com.andhikaaw.androiddevelopertest.data.remote.ApiService
import com.andhikaaw.androiddevelopertest.data.repository.RegionRepository
import com.andhikaaw.androiddevelopertest.view.RegionViewModel
import com.andhikaaw.androiddevelopertest.view.screen.DistrictScreen
import com.andhikaaw.androiddevelopertest.view.screen.RegencyScreen
import com.andhikaaw.androiddevelopertest.view.screen.RegionScreen
import com.andhikaaw.androiddevelopertest.view.screen.VillageScreen
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup DB
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "region_db"
        ).build()

        // setup API
        val api = Retrofit.Builder()
            .baseUrl("https://andhikaaw.github.io/api-wilayah-indonesia/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // repo + viewmodel
        val repo = RegionRepository(api, db)


        val regionViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegionViewModel(repo) as T
            }
        })[RegionViewModel::class.java]

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val showOfflineDialog = remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    if (!isNetworkAvailable(this@MainActivity)) {
                        showOfflineDialog.value = true
                    }
                }
                if (showOfflineDialog.value) {
                    AlertDialog(
                        onDismissRequest = { /* Tidak bisa ditutup dengan back button */ },
                        title = { Text("Koneksi Internet Tidak Ada") },
                        text = { Text("Mohon cek koneksi internet Anda untuk memuat data.") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    if (isNetworkAvailable(this@MainActivity)) {
                                        // Tutup dialog dan coba lagi jika koneksi sudah ada
                                        showOfflineDialog.value = false
                                        navController.navigate("provinces")
                                    }
                                }
                            ) {
                                Text("Coba Lagi")
                            }
                        }
                    )
                }
                NavHost(navController = navController, startDestination = "provinces") {
                    composable("provinces") {
                        RegionScreen(viewModel = regionViewModel, navController = navController)
                    }
                    composable("regencies/{provinceId}") { backStackEntry ->
                        val provinceId = backStackEntry.arguments?.getString("provinceId") ?: ""
                        RegencyScreen(viewModel = regionViewModel, navController = navController, provinceId = provinceId)
                    }
                    composable("districts/{regencyId}") { backStackEntry ->
                        val regencyId = backStackEntry.arguments?.getString("regencyId") ?: ""
                        DistrictScreen(viewModel = regionViewModel, navController = navController, regencyId = regencyId)
                    }
                    composable("villages/{districtId}") { backStackEntry ->
                        val districtId = backStackEntry.arguments?.getString("districtId") ?: ""
                        VillageScreen(viewModel = regionViewModel, districtId = districtId)
                    }
                }
            }
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
