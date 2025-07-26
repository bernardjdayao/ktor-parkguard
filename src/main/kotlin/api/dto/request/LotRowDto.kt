package com.parkguard.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LotRowDto(
    val row_code: String,
    val parking_id: String
)
