package com.parkguard.api.repository
import com.parkguard.api.model.VehicleModel

interface VehicleRepository {
    suspend fun getAll():List<VehicleModel>

    suspend fun getByStickerID(sticker_no: String):VehicleModel?

    suspend fun save(vehicleModel: VehicleModel): Boolean

    suspend fun update(vehicleModel: VehicleModel): Boolean

    suspend fun delete(sticker_no: String): Boolean

    suspend fun vehicleExists(sticker_no: String): Boolean

}