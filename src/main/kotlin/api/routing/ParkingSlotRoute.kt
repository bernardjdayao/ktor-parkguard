package com.parkguard.api.routing

import com.parkguard.api.dto.request.ParkingSlotDto
import com.parkguard.api.dto.response.ApiResponse
import com.parkguard.api.dto.response.toHttpCode
import com.parkguard.api.service.ParkingSlotService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.parkingSlotRoute(parkingSlotService: ParkingSlotService){
    get {
        val responsePayload = parkingSlotService.getAllParkingSlot()
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    get ("/{slot_no}"){

        val responsePayload = parkingSlotService.getByParkingSlot(call.parameters["slot_no"]?.toInt()?: 0)
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }

    post {
        val request = call.receive<ParkingSlotDto>()
        val responsePayload = parkingSlotService.putIntoParkingSlot(parkingSlotDto = request)
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    put ("/parkthecar"){
        val request= call.receive<ParkingSlotDto>()
        val responsePayload = parkingSlotService.updateParkingSlot(request)
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    get ("check/{sticker_no}/{parking_id}"){
        val sticker_no = call.parameters["sticker_no"]?:""
        val parking_id = call.parameters["parking_id"]?:""
        if (sticker_no.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Missing or empty sticker number")
        }

        val result = parkingSlotService.isParked(sticker_no, parking_id)
        call.respond(result.code.toHttpCode(), result)
    }

    get("/parking-info/{row_code}/{parking_id}"){
        val row_code = call.parameters["row_code"]
        val parking_id = call.parameters["parking_id"]
        if(row_code==null || parking_id==null ){
            call.respond(HttpStatusCode.BadRequest, "Missing parameters")
            return@get
        }
        val responsePayload = parkingSlotService.getParkingInfo(row_code, parking_id)
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    get("/available-slots/{parking_id}"){
        val parking_id = call.parameters["parking_id"]
        if(parking_id==null){
            call.respond(HttpStatusCode.BadRequest, ApiResponse<Unit>(
                code = 400,
                message = "Missing parameter"
            ))
            return@get
        }
        try{
            val responsePayload = parkingSlotService.getAllAvailableParkingSlots(parking_id)
            call.respond(responsePayload.code.toHttpCode(), responsePayload)
        }catch (e: Exception){
            call.respond(HttpStatusCode.InternalServerError, ApiResponse<Unit>(
                code = 500,
                message = "Failed to retrieve available slot count: ${e.message}"
            ))
        }
    }
}