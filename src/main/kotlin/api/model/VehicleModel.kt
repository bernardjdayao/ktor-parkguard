package com.parkguard.api.model

data class VehicleModel(
    val plateNumber: String,
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val sticker: Int,
    val ownerId: String
)
