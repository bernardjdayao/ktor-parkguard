package com.parkguard.api.repository.impl

import com.parkguard.api.database.DatabaseFactory.query
import com.parkguard.api.database.rowToModelParking
import com.parkguard.api.database.table.ParkingLotTable
import com.parkguard.api.model.ParkingLotModel
import com.parkguard.api.repository.ParkingRepository
import org.jetbrains.exposed.sql.selectAll

class ParkingRepositoryImpl : ParkingRepository {
    val parkMap = mutableMapOf<String, ParkingLotModel>()
    override suspend fun getAll(): List<ParkingLotModel> =
        query {
            ParkingLotTable.selectAll().map {
                rowToModelParking(it)
            }
        }

    override suspend fun getByParkingId(parking_id: String): ParkingLotModel? =
        query {
            ParkingLotTable.selectAll().where{ParkingLotTable.parking_id eq parking_id}.map(::rowToModelParking).singleOrNull()
        }
}