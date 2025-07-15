package com.parkguard.api.dto.requestdto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String?=null,
    val firstname: String,
    val lastname: String,
    val age: Int
)