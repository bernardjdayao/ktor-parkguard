package com.parkguard

import com.parkguard.api.plugin.configureRouting
import com.parkguard.api.plugin.configureSerialization
import com.parkguard.api.repository.VehicleRepository
import com.parkguard.api.repository.impl.UserRepositoryImpl
import com.parkguard.api.repository.impl.VehicleRepositoryImpl
import com.parkguard.api.service.UserService
import com.parkguard.api.service.VehicleService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userRepository = UserRepositoryImpl()
    val vehicleRepository = VehicleRepositoryImpl()
    val userService= UserService(userRepository)
    val vehicleService = VehicleService(vehicleRepository)
    configureSerialization()
    configureRouting(userService, vehicleService)
}
