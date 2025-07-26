package com.parkguard.api.service

import com.parkguard.api.dto.request.LotRowDto
import com.parkguard.api.dto.response.ApiResponse
import com.parkguard.api.dto.response.mapToDto
import com.parkguard.api.repository.LotRowRepository
import org.jetbrains.exposed.sql.Except

class LotRowService(private val lotRowRepository: LotRowRepository) {
    suspend fun getAllLotRow() : ApiResponse<List<LotRowDto>>{
        val lotRowList = lotRowRepository.getAll().map{
            it.mapToDto()
        }
        return ApiResponse(data=lotRowList)
    }

    suspend fun getRowCodeByParkingId(parking_id: String): ApiResponse<List<String>>{
        return try {
            val response = lotRowRepository.getRowCodeByParkingId(parking_id)
            ApiResponse(
                code = 200,
                message = "Row codes retrieved",
                data = response
            )
        }catch(e: Exception){
            ApiResponse(
                code = 500,
                message = "Error retieving row codes ${e.message}",
                data = emptyList()
            )
        }
    }

    suspend fun getLotRow(row_code: String, parking_id: String) : ApiResponse<LotRowDto>{
        return try {
            lotRowRepository.getByRowCode(row_code, parking_id)?.let { lotRowModel ->
                ApiResponse(
                    data = lotRowModel.mapToDto(),
                    message = "Lot received successfully")
            }?: ApiResponse(
                code = 404,
                message = "Error retrieving lot $row_code not found"
            )
        }catch (e: Exception){
            System.err.println("Error retrieving lot $row_code: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to retrieve lot: ${e.message ?: "Unknown error"}")
        }
    }
}