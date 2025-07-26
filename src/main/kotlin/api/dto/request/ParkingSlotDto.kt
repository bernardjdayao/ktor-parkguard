package com.parkguard.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ParkingSlotDto(
    val slot_no: Int,
    val row_code: String,
    val parking_id: String,
    val is_available: Boolean,
    val sticker_no: String
)
