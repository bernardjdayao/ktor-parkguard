package com.parkguard.api.plugin

import com.parkguard.api.routing.lotrowRoute
import com.parkguard.api.routing.parkingRoute
import com.parkguard.api.routing.parkingSlotRoute
import com.parkguard.api.routing.userRoute
import com.parkguard.api.routing.vehicleRoute
import com.parkguard.api.service.LotRowService
import com.parkguard.api.service.ParkingService
import com.parkguard.api.service.ParkingSlotService
import com.parkguard.api.service.UserService
import com.parkguard.api.service.VehicleService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, vehicleService: VehicleService, parkingService: ParkingService, lotRowService: LotRowService, parkingSlotService: ParkingSlotService) {
    routing{
        route("/api") {
            route("/users") {
                    userRoute(userService)
                }
            route("/vehicles"){
                vehicleRoute(vehicleService)
            }
            route("/parking"){
                parkingRoute(parkingService)
            }
            route("/lotrow"){
                lotrowRoute(lotRowService)
            }
            route("/parkingslot"){
                parkingSlotRoute(parkingSlotService)
            }
        }
    }
}
