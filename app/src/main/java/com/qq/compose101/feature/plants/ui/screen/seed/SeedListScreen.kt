@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)

package com.qq.compose101.feature.plants.ui.screen.seed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.qq.compose101.core.theme.title
import com.qq.compose101.feature.plants.domain.entity.Plant
import com.qq.compose101.feature.plants.ui.model.PlantView
import com.qq.compose101.feature.plants.ui.viewModel.PlantListViewModel
import com.qq.compose101.feature.app.ui.widget.RemoteImage

@Composable
fun SeedListScreen(
    modifier: Modifier = Modifier,
    onPlantClick: (PlantView) -> Unit,
    viewModel: PlantListViewModel = hiltViewModel()
) {
    val plants by viewModel.plants.collectAsState(initial = emptyList())
    SeedListScreen(plantViews = plants, onPlantClick = onPlantClick, modifier = modifier)
}

@Composable
fun SeedListScreen(
    plantViews: List<PlantView>, modifier: Modifier = Modifier, onPlantClick: (PlantView) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
        modifier = modifier
            .testTag("plant_list")
            .imePadding(),
        userScrollEnabled = true
    ) {
        items(items = plantViews, key = { it.plantId }) {
            SeedListItem(plantView = it) {
                onPlantClick(it)
            }
        }
    }
}

@Composable
fun SeedListItem(plantView: PlantView, onClick: () -> Unit) {
    ImageListItem(name = plantView.name, imageUrl = plantView.imageUrl) {
        onClick()
    }
}

@Composable
fun ImageListItem(name: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        onClick = onClick, modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 16.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            RemoteImage(
                model = imageUrl,
                contentDescription = "plant",
                Modifier
                    .fillMaxWidth()
                    .height(95.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

}