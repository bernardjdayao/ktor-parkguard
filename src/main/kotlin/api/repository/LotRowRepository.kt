package com.parkguard.api.repository

import com.parkguard.api.model.LotRowModel

interface LotRowRepository {
    suspend fun getAll():List<LotRowModel>

    suspend fun getByRowCode(row_code:String, parking_id: String): LotRowModel?

    suspend fun getRowCodeByParkingId(parking_id:String): List<String>?
}