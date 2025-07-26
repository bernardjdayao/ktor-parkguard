package com.parkguard.api.routing

import com.parkguard.api.dto.response.toHttpCode
import com.parkguard.api.service.LotRowService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.lotrowRoute(lotRowService: LotRowService){
    get {
        val responsePayload = lotRowService.getAllLotRow()
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    get ("/{row_code}/{parking_id}"){
        val row_code = call.parameters["row_code"]
        val parking_id = call.parameters["parking_id"]
        if (row_code == null || parking_id == null) {
            call.respond(HttpStatusCode.BadRequest, "Missing path parameters.")
            return@get
        }
        val responsePayload = lotRowService.getLotRow(row_code, parking_id)
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }

    get("/parkingid/{parking_id}"){
        val parking_id = call.parameters["parking_id"]
        if(parking_id==null){
            call.respond(HttpStatusCode.BadRequest, "Missing path parameters")
            return@get
        }
        val responsePayload = lotRowService.getRowCodeByParkingId(parking_id)
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }
}