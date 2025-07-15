package com.parkguard.api.dto.response

import io.ktor.http.HttpStatusCode

fun Int.toHttpCode(): HttpStatusCode= HttpStatusCode.fromValue(this)