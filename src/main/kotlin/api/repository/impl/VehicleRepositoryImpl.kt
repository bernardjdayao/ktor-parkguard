package com.parkguard.api.repository.impl

import com.parkguard.api.model.VehicleModel
import com.parkguard.api.repository.VehicleRepository
import com.parkguard.api.database.DatabaseFactory.query
import com.parkguard.api.database.rowToModelVehicle
import com.parkguard.api.database.table.VehicleTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class VehicleRepositoryImpl: VehicleRepository {
    val vehicleMap = mutableMapOf<String, VehicleModel>()
    override suspend fun getAll(): List<VehicleModel> =
        query{
            VehicleTable.selectAll().map {
                rowToModelVehicle(it)
            }
        }

    override suspend fun getByStickerID(sticker_no: String): VehicleModel? =
        query{
            VehicleTable.selectAll().where{ VehicleTable.sticker_no eq sticker_no }.map(::rowToModelVehicle).singleOrNull()
        }

    override suspend fun save(vehicleModel: VehicleModel): Boolean {
        return query {
            VehicleTable.insert {
                it[sticker_no] = vehicleModel.sticker_no
                it[plate_number] = vehicleModel.plate_number
                it[brand] = vehicleModel.brand
                it[model] = vehicleModel.model
                it[color] = vehicleModel.color
                it[year] = vehicleModel.year
                it[id] = vehicleModel.id
            }.insertedCount > 0
        }
    }

    override suspend fun update(vehicleModel: VehicleModel): Boolean =
        query{
            VehicleTable.update({ VehicleTable.sticker_no eq vehicleModel.sticker_no}){
                it[sticker_no] = vehicleModel.sticker_no
                it[plate_number] = vehicleModel.plate_number
                it[brand] = vehicleModel.brand
                it[model] = vehicleModel.model
                it[color] = vehicleModel.color
                it[year] = vehicleModel.year
                it[id] = vehicleModel.id
            } > 0
        }

    override suspend fun delete(sticker_no: String): Boolean =
        query {
            VehicleTable.deleteWhere{
                VehicleTable.sticker_no eq sticker_no
            } > 0
        }

    override suspend fun vehicleExists(sticker_no: String): Boolean =
        query {
            VehicleTable.select(VehicleTable.sticker_no).where{
                VehicleTable.sticker_no eq sticker_no
            }.count() > 0
        }
}