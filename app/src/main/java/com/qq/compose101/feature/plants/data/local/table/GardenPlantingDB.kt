package com.qq.compose101.feature.plants.data.local.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.qq.compose101.feature.plants.domain.entity.GardenPlanting
import java.util.Calendar

/**
 * [GardenPlantingDB] represents when a user adds a [PlantDB] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * plant).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "garden_plantings",
    foreignKeys = [
        ForeignKey(entity = PlantDB::class, parentColumns = ["id"], childColumns = ["plant_id"])
    ],
    indices = [Index("plant_id")]
)
data class GardenPlantingDB(
    @ColumnInfo(name = "plant_id") val plantId: String,

    /**
     * Indicates when the [PlantDB] was planted. Used for showing notification when it's time
     * to harvest the plant.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [PlantDB] was last watered. Used for showing notification when it's
     * time to water the plant.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlantingId: Long = 0
}

fun GardenPlantingDB.convert(): GardenPlanting {
    return GardenPlanting(
        plantId = plantId,
        plantDate = plantDate,
        lastWateringDate = lastWateringDate,
        gardenPlantingId = gardenPlantingId
    )
}
