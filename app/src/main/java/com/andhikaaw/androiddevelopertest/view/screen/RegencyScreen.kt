package com.andhikaaw.androiddevelopertest.view.screen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.andhikaaw.androiddevelopertest.data.model.Regency
import com.andhikaaw.androiddevelopertest.view.RegionViewModel

@Composable
fun RegencyScreen(navController: NavController, provinceId: String, viewModel: RegionViewModel) {
    val regencies by viewModel.regencies(provinceId).collectAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }
    // filter data sesuai search
    val filteredList = regencies.filter { it.name.contains(searchQuery, ignoreCase = true) }

    LaunchedEffect(provinceId) {
        viewModel.loadRegencies(provinceId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(
        top = 30.dp,
        start = 16.dp,
        end = 16.dp,
        bottom = 16.dp
    )) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Daftar Kabupaten / Kota",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Cari Kabupaten / Kota...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tidak ada data ditemukan", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredList) { regency ->
                    RegencyItem (
                        regency = regency,
                        onClick = {
                            navController.navigate("districts/${regency.id}")
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun RegencyItem(regency: Regency, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = regency.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}