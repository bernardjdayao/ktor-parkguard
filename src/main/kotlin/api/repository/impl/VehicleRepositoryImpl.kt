package com.parkguard.api.repository.impl

import com.parkguard.api.model.VehicleModel
import com.parkguard.api.repository.VehicleRepository

class VehicleRepositoryImpl: VehicleRepository {
    val vehicleMap = mutableMapOf<Int, VehicleModel>()
    override suspend fun getAll(): List<VehicleModel> {
        return vehicleMap.values.toList()
    }

    override suspend fun getByStickerID(sticker: Int): VehicleModel? {
        return vehicleMap.get(sticker)
    }

    override suspend fun save(vehicleModel: VehicleModel): Boolean {
        val result = vehicleMap.put(vehicleModel.sticker, vehicleModel)
        return result!=null
    }

    override suspend fun update(vehicleModel: VehicleModel): Boolean {
        val result = vehicleMap.replace(vehicleModel.sticker, vehicleModel)
        return result!=null
    }

    override suspend fun delete(sticker: Int): Boolean {
        val result = vehicleMap.remove(key=sticker)
        return result!=null
    }

    override suspend fun vehicleExists(sticker: Int): Boolean {
        return vehicleMap.containsKey(sticker)
    }

}