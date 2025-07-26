package com.parkguard.api.dto.requestdto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val year: String,
    val program: String
)