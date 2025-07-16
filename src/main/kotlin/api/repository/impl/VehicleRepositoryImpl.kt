package com.parkguard.api.repository.impl

import com.parkguard.api.model.VehicleModel
import com.parkguard.api.repository.VehicleRepository

class VehicleRepositoryImpl: VehicleRepository {
    val vehicleMap = mutableMapOf<String, VehicleModel>()
    override suspend fun getAll(): List<VehicleModel> {
        return vehicleMap.values.toList()
    }

    override suspend fun getByPlateNumber(plateNumber: String): VehicleModel? {
        return vehicleMap.get(plateNumber)
    }

    override suspend fun save(vehicleModel: VehicleModel): Boolean {
        val result = vehicleMap.put(vehicleModel.plateNumber, vehicleModel)
        return result!=null
    }

    override suspend fun update(vehicleModel: VehicleModel): Boolean {
        val result = vehicleMap.replace(vehicleModel.plateNumber, vehicleModel)
        return result!=null
    }

    override suspend fun delete(plateNumber: String): Boolean {
        val result = vehicleMap.remove(key=plateNumber)
        return result!=null
    }

    override suspend fun vehicleExists(plateNumber: String): Boolean {
        return vehicleMap.containsKey(plateNumber)
    }

}