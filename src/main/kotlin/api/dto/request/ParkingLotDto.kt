package com.parkguard.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ParkingLotDto(
    val parking_id: String,
    val parking_name: String
)
