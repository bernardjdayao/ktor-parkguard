package com.parkguard.api.plugin

import com.parkguard.api.routing.userRoute
import com.parkguard.api.routing.vehicleRoute
import com.parkguard.api.service.UserService
import com.parkguard.api.service.VehicleService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, vehicleService: VehicleService) {
    routing{
        route("/api") {
            route("/users") {
                    userRoute(userService)
                }
            route("/vehicles"){
                vehicleRoute(vehicleService)
            }
        }
    }
}
