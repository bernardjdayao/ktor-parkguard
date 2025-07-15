package com.parkguard.api.routing

import com.parkguard.api.dto.requestdto.UserDto
import com.parkguard.api.dto.response.toHttpCode
import com.parkguard.api.service.UserService
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.userRoute(userService: UserService) {
    get {
        val responsePayload = userService.getAllUser()
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    post {
        val request = call.receive<UserDto>()
        val responsePayload = userService.createUser(userDto = request)
        call.respond(responsePayload.code.toHttpCode(), responsePayload.data?:responsePayload.message!!)
    }

    get("/{id}") {
        val responsePayload = userService.getUser(call.parameters["id"])
        call.respond(responsePayload.code.toHttpCode(), responsePayload)
    }

    put("/{id}") {
        val requestPayload = call.receive<UserDto>()
        val responsePayload = userService.updateUser(call.parameters["id"],requestPayload)
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }

    delete("/{id}") {
        val responsePayload = userService.deleteUser(call.parameters["id"])
        call.respond(responsePayload.code.toHttpCode(),responsePayload)
    }
}
