package com.qq.compose101.feature.plants.data.di

import com.qq.compose101.feature.plants.data.repositoryimpl.GardenRepositoryImpl
import com.qq.compose101.feature.plants.domain.repository.PlantRepository
import com.qq.compose101.feature.plants.data.repositoryimpl.PlantRepositoryImpl
import com.qq.compose101.feature.plants.domain.repository.GardenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindPlantRepository(
        plantRepositoryImpl: PlantRepositoryImpl
    ): PlantRepository

    @Binds
    abstract fun bindGardenRepository(
        gardenRepositoryImpl: GardenRepositoryImpl
    ): GardenRepository
}