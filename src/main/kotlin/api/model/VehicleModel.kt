package com.parkguard.api.model

data class VehicleModel(
    val sticker: Int,
    val plateNumber: String,
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val ownerId: String
)
