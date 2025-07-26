package com.parkguard.api.routing

import com.parkguard.api.dto.response.toHttpCode
import com.parkguard.api.service.ParkingService
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.parkingRoute(parkingService: ParkingService){
    get {
        val responsePayload = parkingService.getAllParkingLot()
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }
    get("/{parking_id}"){
        val responsePayload = parkingService.getParking(call.parameters["parking_id"]?: "")
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }
}