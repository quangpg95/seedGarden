@file:OptIn(
    ExperimentalGlideComposeApi::class,
    ExperimentalGlideComposeApi::class,
    ExperimentalMaterial3Api::class
)

package com.qq.compose101.feature.plants.ui.screen.garden

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.qq.compose101.R
import com.qq.compose101.core.theme.body
import com.qq.compose101.core.theme.caption
import com.qq.compose101.core.theme.headline
import com.qq.compose101.core.theme.title
import com.qq.compose101.feature.plants.ui.model.PlantAndGardenPlantingsView
import com.qq.compose101.feature.plants.ui.viewModel.GardenViewModel
import com.qq.compose101.feature.app.ui.widget.RemoteImage

@Composable
fun GardenScreen(
    modifier: Modifier = Modifier,
    viewModel: GardenViewModel = hiltViewModel(),
    onAddPlantClick: () -> Unit,
    onPlantClick: (PlantAndGardenPlantingsView) -> Unit
) {
    val gardenPlants by viewModel.gardenPlantingState.collectAsState(initial = emptyList())
    GardenScreen(
        gardenPlants = gardenPlants,
        onAddPlantClick = onAddPlantClick,
        onPlantClick = onPlantClick,
        modifier = modifier
    )
}

@Composable
fun GardenScreen(
    gardenPlants: List<PlantAndGardenPlantingsView>,
    modifier: Modifier = Modifier,
    onAddPlantClick: () -> Unit,
    onPlantClick: (PlantAndGardenPlantingsView) -> Unit
) {
    if (gardenPlants.isEmpty()) {
        EmptyGarden(onAddPlantClick = onAddPlantClick, modifier = modifier)
    } else {
        GardenList(gardenPlants = gardenPlants, onPlantClick = onPlantClick)
    }
}

@Composable
fun GardenList(
    gardenPlants: List<PlantAndGardenPlantingsView>,
    onPlantClick: (PlantAndGardenPlantingsView) -> Unit,
    modifier: Modifier = Modifier
) {
    // Call reportFullyDrawn when the garden list has been rendered
    val gridState = rememberLazyGridState()
    ReportDrawnWhen { gridState.layoutInfo.totalItemsCount > 0 }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), contentPadding = PaddingValues(
            horizontal = 12.dp, vertical = 16.dp
        )
    ) {
        items(items = gardenPlants, key = { it.plantView.plantId }) {
            GardenListItem(plantings = it, onPlantClick = onPlantClick)
        }
    }
}


@Composable
fun GardenListItem(
    plantings: PlantAndGardenPlantingsView, onPlantClick: (PlantAndGardenPlantingsView) -> Unit
) {
    plantings.apply {
        val gardenView = plantings.gardenPlantingViews[0]
        ElevatedCard(
            onClick = { onPlantClick(this) },
            modifier = Modifier.padding(
                start = 12.dp, end = 12.dp, bottom = 16.dp
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(Modifier.fillMaxWidth()) {
                RemoteImage(
                    model = plantView.imageUrl,
                    contentDescription = plantView.description,
                    Modifier
                        .fillMaxWidth()
                        .height(95.dp),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = plantView.name,
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.title
                )

                Text(
                    text = stringResource(id = R.string.title_planted),
                    Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.body
                )

                Text(
                    text = gardenView.plantDate,
                    Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.caption
                )

                // Last Watered
                Text(
                    text = stringResource(id = R.string.watered_date_header),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    style = MaterialTheme.typography.body
                )
                Text(
                    text = gardenView.lastWateringDate,
                    Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = pluralStringResource(
                        id = R.plurals.watering_next,
                        count = plantView.wateringInterval,
                        plantView.wateringInterval
                    ),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }

}

@Composable
fun EmptyGarden(onAddPlantClick: () -> Unit, modifier: Modifier = Modifier) {
    // Calls reportFullyDrawn when this composable is composed.
    ReportDrawn()

    Column(
        Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.title_garden_empty),
            style = MaterialTheme.typography.headline
        )
        Button(onClick = onAddPlantClick) {
            Text(
                text = stringResource(id = R.string.title_add_plant),
                style = MaterialTheme.typography.title
            )
        }
    }

}
