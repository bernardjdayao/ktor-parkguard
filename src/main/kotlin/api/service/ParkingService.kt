package com.parkguard.api.service

import com.parkguard.api.dto.request.ParkingLotDto
import com.parkguard.api.dto.response.ApiResponse
import com.parkguard.api.dto.response.mapToDto
import com.parkguard.api.repository.ParkingRepository

class ParkingService(private val parkingRepository: ParkingRepository) {
    suspend fun getAllParkingLot(): ApiResponse<List<ParkingLotDto>>{
        val parklist = parkingRepository.getAll().map {
            it.mapToDto()
        }
        return ApiResponse(data=parklist)
    }

    suspend fun getParking(parking_id:String): ApiResponse<ParkingLotDto>{
        return try{
            parkingRepository.getByParkingId(parking_id)?.let{ parkingLotModel ->
                ApiResponse(
                    data = parkingLotModel.mapToDto(),
                    message = "Parking lot retrieved succesfully"
                )
            } ?: ApiResponse(
                code = 404,
                message = "Error retrieving Parking $parking_id not found."
            )
        } catch(e: Exception){
            System.err.println("Error retrieving parking $parking_id: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to parking lot: ${e.message ?: "Unknown error"}")
        }
    }
}