package com.parkguard

import com.parkguard.api.database.DatabaseFactory
import com.parkguard.api.plugin.configureRouting
import com.parkguard.api.plugin.configureSerialization
import com.parkguard.api.repository.ParkingSlotRepository
import com.parkguard.api.repository.VehicleRepository
import com.parkguard.api.repository.impl.LotRowRepositoryImpl
import com.parkguard.api.repository.impl.ParkingRepositoryImpl
import com.parkguard.api.repository.impl.ParkingSlotRepositoryImpl
import com.parkguard.api.repository.impl.UserRepositoryImpl
import com.parkguard.api.repository.impl.VehicleRepositoryImpl
import com.parkguard.api.service.LotRowService
import com.parkguard.api.service.ParkingService
import com.parkguard.api.service.ParkingSlotService
import com.parkguard.api.service.UserService
import com.parkguard.api.service.VehicleService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    DatabaseFactory.init()
    val userRepository = UserRepositoryImpl()
    val vehicleRepository = VehicleRepositoryImpl()
    val parkingRepository = ParkingRepositoryImpl()
    val lotRowRepository = LotRowRepositoryImpl()
    val parkingSlotRepository = ParkingSlotRepositoryImpl()
    val userService= UserService(userRepository)
    val vehicleService = VehicleService(vehicleRepository)
    val parkingService = ParkingService(parkingRepository)
    val lotRowService = LotRowService(lotRowRepository)
    val parkingSlotService = ParkingSlotService(parkingSlotRepository)
    configureSerialization()
    configureRouting(userService, vehicleService, parkingService, lotRowService, parkingSlotService)
}
