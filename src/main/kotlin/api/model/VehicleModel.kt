package com.parkguard.api.model

data class VehicleModel(
    val sticker_no: String,
    val plate_number: String,
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val id: String
)
