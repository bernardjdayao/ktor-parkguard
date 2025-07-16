package com.parkguard.api.repository
import com.parkguard.api.model.VehicleModel

interface VehicleRepository {
    suspend fun getAll():List<VehicleModel>

    suspend fun getByPlateNumber(plateNumber:String):VehicleModel?

    suspend fun save(vehicleModel: VehicleModel): Boolean

    suspend fun update(vehicleModel: VehicleModel): Boolean

    suspend fun delete(plateNumber: String): Boolean

    suspend fun vehicleExists(plateNumber: String): Boolean

}