package com.qq.compose101.feature.plants.data.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qq.compose101.feature.plants.domain.entity.Plant
import java.util.Calendar

@Entity(tableName = "plants")
data class PlantDB(
    @PrimaryKey @ColumnInfo(name = "id") val plantId: String,
    val name: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int = 7, // how often the plant should be watered, in days
    val imageUrl: String = ""
) {
    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(Calendar.DAY_OF_YEAR, wateringInterval) }

    override fun toString() = name
}

fun PlantDB.convert() = Plant(
    plantId = plantId,
    name = name,
    description = description,
    growZoneNumber = growZoneNumber,
    wateringInterval = wateringInterval,
    imageUrl = imageUrl
)