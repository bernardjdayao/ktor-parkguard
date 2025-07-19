package com.parkguard.api.repository
import com.parkguard.api.model.VehicleModel

interface VehicleRepository {
    suspend fun getAll():List<VehicleModel>

    suspend fun getByStickerID(sticker: Int):VehicleModel?

    suspend fun save(vehicleModel: VehicleModel): Boolean

    suspend fun update(vehicleModel: VehicleModel): Boolean

    suspend fun delete(sticker: Int): Boolean

    suspend fun vehicleExists(sticker: Int): Boolean

}