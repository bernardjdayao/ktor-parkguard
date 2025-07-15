package com.parkguard.api.plugin

import com.parkguard.api.routing.userRoute
import com.parkguard.api.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService) {
    routing{
        route("/api") {
            route("/users") {
                userRoute(userService)
            }
        }
    }
}
