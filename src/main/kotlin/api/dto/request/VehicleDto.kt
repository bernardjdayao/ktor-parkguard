package com.parkguard.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val sticker_no: String,
    val plate_number: String,
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val id: String
)
