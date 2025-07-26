package com.parkguard.api.repository

import com.parkguard.api.model.ParkingSlotModel

interface ParkingSlotRepository {
    suspend fun getAll():List<ParkingSlotModel>

    suspend fun getAllAvailableSlots(parking_id:String): Int?

    suspend fun getParkingSlotInfo(row_code: String, parking_id: String): List<ParkingSlotModel>?

    suspend fun getBySlotNo(slot_no: Int): List<ParkingSlotModel>?

    suspend fun putIntoParking(parkingSlotModel: ParkingSlotModel): Boolean

    suspend fun exitParking(slot_no: Int, row_code: String, parking_id: String): Boolean

    suspend fun entryParking(sticker_no: String, slot_no: Int, row_code: String, parking_id: String): Boolean

    suspend fun isParked(sticker_no: String, parking_id: String): Boolean

    suspend fun getParkedVehicle(sticker_no:String): ParkingSlotModel?
}