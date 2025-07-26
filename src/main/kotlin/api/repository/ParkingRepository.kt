package com.parkguard.api.repository

import com.parkguard.api.model.ParkingLotModel

interface ParkingRepository {
    suspend fun getAll():List<ParkingLotModel>

    suspend fun getByParkingId(parking_id:String):ParkingLotModel?
}