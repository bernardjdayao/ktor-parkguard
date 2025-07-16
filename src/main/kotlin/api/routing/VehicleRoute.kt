package com.parkguard.api.routing

import com.parkguard.api.dto.request.VehicleDto
import com.parkguard.api.dto.response.toHttpCode
import com.parkguard.api.service.VehicleService
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.vehicleRoute(vehicleService: VehicleService){

    get {
        val responsePayload = vehicleService.getAllVehicle()
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    post {
        val request = call.receive<VehicleDto>()
        val responsePayload = vehicleService.createVehicle(request)
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }

    get("/{plateNumber}") {
        val responsePayload = vehicleService.getVehicle(call.parameters["plateNumber"].toString())
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }

    put("/{plateNumber}"){
        val request = call.receive<VehicleDto>()
        val responsePayload = vehicleService.updateVehicle(call.parameters["plateNumber"].toString(),request)
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }

    delete("/{plateNumber}") {
        val responsePayload = vehicleService.deleteVehicle(call.parameters["plateNumber"].toString())
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }
}