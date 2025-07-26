package com.parkguard.api.model

data class ParkingSlotModel(
    val slot_no: Int,
    val row_code: String,
    val parking_id: String,
    val is_available: Boolean,
    val sticker_no: String
)
