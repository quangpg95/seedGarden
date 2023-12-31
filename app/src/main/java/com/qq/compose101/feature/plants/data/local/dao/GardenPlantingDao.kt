/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qq.compose101.feature.plants.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.qq.compose101.feature.plants.data.local.table.GardenPlantingDB
import com.qq.compose101.feature.plants.data.local.table.PlantAndGardenPlantingsDB
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [GardenPlantingDB] class.
 */
@Dao
interface GardenPlantingDao {
    @Query("SELECT * FROM garden_plantings")
    fun getGardenPlantings(): Flow<List<GardenPlantingDB>>

    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: String): Flow<Boolean>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlantingDB] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    fun getPlantedGardens(): Flow<List<PlantAndGardenPlantingsDB>>

    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlantingDB): Long

    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlantingDB)
}
