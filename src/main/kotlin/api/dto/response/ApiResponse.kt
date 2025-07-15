package com.parkguard.api.dto.response


import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val code: Int = 200,
    val message: String? = "Processed Succesfully",
    val data: T? = null
)
