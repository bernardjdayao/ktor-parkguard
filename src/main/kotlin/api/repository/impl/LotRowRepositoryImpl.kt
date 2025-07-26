package com.parkguard.api.repository.impl

import com.parkguard.api.model.LotRowModel
import com.parkguard.api.repository.LotRowRepository
import com.parkguard.api.database.DatabaseFactory.query
import com.parkguard.api.database.rowToModelLotRow
import com.parkguard.api.database.table.LotRowTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll

class LotRowRepositoryImpl : LotRowRepository {
    override suspend fun getAll(): List<LotRowModel> =
        query {
            LotRowTable.selectAll().map {
                rowToModelLotRow(it)
            }
        }

    override suspend fun getByRowCode(row_code: String,parking_id: String): LotRowModel? =
        query {
            LotRowTable.selectAll().where { (LotRowTable.row_code eq row_code) and (LotRowTable.parking_id eq parking_id) }.map(::rowToModelLotRow).singleOrNull()
        }

    override suspend fun getRowCodeByParkingId(parking_id: String): List<String>? =
        query{
            LotRowTable.select(LotRowTable.row_code)
                .where{ LotRowTable.parking_id eq parking_id }
                .map{it[LotRowTable.row_code]}
        }
}