package com.parkguard.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val plateNumber: String,
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val sticker: Int,
    val ownerId: String
)
