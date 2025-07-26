package com.parkguard.api.repository.impl

import com.parkguard.api.model.ParkingSlotModel
import com.parkguard.api.repository.ParkingSlotRepository
import com.parkguard.api.database.DatabaseFactory.query
import com.parkguard.api.database.rowToModelParkingSlot
import com.parkguard.api.database.table.ParkingSlotTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.slice

class ParkingSlotRepositoryImpl : ParkingSlotRepository {
    override suspend fun getAll(): List<ParkingSlotModel> =
        query {
            ParkingSlotTable.selectAll().map {
                rowToModelParkingSlot(it)
            }
        }

    override suspend fun getAllAvailableSlots(parking_id: String): Int = query {
        ParkingSlotTable
            .select((ParkingSlotTable.is_available eq true) and (ParkingSlotTable.parking_id eq parking_id))
            .where { (ParkingSlotTable.is_available eq true) and (ParkingSlotTable.parking_id eq parking_id) }
            .count()
            .toInt()
    }

    override suspend fun getParkingSlotInfo(
        row_code: String,
        parking_id: String
    ): List<ParkingSlotModel>? =
        query {
            ParkingSlotTable.selectAll()
                .where { (ParkingSlotTable.row_code eq row_code) and (ParkingSlotTable.parking_id eq parking_id)}
                .orderBy(ParkingSlotTable.slot_no to SortOrder.ASC)
                .map {
                    rowToModelParkingSlot(it)
                }
        }


    override suspend fun getBySlotNo(slot_no: Int): List<ParkingSlotModel>?=
        query {
            ParkingSlotTable.selectAll().where { ParkingSlotTable.slot_no eq slot_no }
                .map{rowToModelParkingSlot(it)}
        }

    override suspend fun putIntoParking(parkingSlotModel: ParkingSlotModel): Boolean {
        return query {
            ParkingSlotTable.insert {
                it[slot_no] = parkingSlotModel.slot_no
                it[row_code] = parkingSlotModel.row_code
                it[parking_id] = parkingSlotModel.parking_id
                it[is_available] = parkingSlotModel.is_available
                it[sticker_no] = parkingSlotModel.sticker_no
            }.insertedCount > 0
        }
    }

    override suspend fun exitParking(
        slot_no: Int,
        row_code: String,
        parking_id: String
    ): Boolean =
        query {
            ParkingSlotTable.update({ (ParkingSlotTable.slot_no eq slot_no) and (ParkingSlotTable.row_code eq row_code) and (ParkingSlotTable.parking_id eq parking_id) }) {
                it[ParkingSlotTable.is_available] = true
                it[ParkingSlotTable.sticker_no] = ""
            } > 0
        }

    override suspend fun entryParking (
        sticker_no: String,
        slot_no: Int,
        row_code: String,
        parking_id: String
    ): Boolean =
        query {
            ParkingSlotTable.update({ (ParkingSlotTable.slot_no eq slot_no) and (ParkingSlotTable.row_code eq row_code) and (ParkingSlotTable.parking_id eq parking_id) }) {
                it[ParkingSlotTable.is_available] = false
                it[ParkingSlotTable.sticker_no] = sticker_no
            } > 0
        }


    override suspend fun isParked(sticker_no: String, parking_id: String): Boolean =
        query {
            ParkingSlotTable.select(ParkingSlotTable.sticker_no).where {
                (ParkingSlotTable.sticker_no eq sticker_no ) and (ParkingSlotTable.parking_id eq parking_id)
            }.count() > 0
        }

    override suspend fun getParkedVehicle(sticker_no: String): ParkingSlotModel? =
        query {
            ParkingSlotTable.select (ParkingSlotTable.sticker_no)
                .where { ParkingSlotTable.sticker_no eq sticker_no }
                .map {
                    ParkingSlotModel(
                        slot_no = it[ParkingSlotTable.slot_no],
                        row_code = it[ParkingSlotTable.row_code],
                        parking_id = it[ParkingSlotTable.parking_id],
                        is_available = it[ParkingSlotTable.is_available],
                        sticker_no = it[ParkingSlotTable.sticker_no]
                    )
                }.single()
        }
}
