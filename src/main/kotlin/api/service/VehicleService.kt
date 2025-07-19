package com.parkguard.api.service

import com.parkguard.api.dto.request.VehicleDto
import com.parkguard.api.dto.response.ApiResponse
import com.parkguard.api.dto.response.mapToDto
import com.parkguard.api.dto.response.mapToModel
import com.parkguard.api.repository.VehicleRepository

class VehicleService(private val vehicleRepository: VehicleRepository) {
    suspend fun getAllVehicle(): ApiResponse<List<VehicleDto>> {
        val list = vehicleRepository.getAll().map {
            it.mapToDto()
        }
        return ApiResponse(data = list)
    }

    suspend fun createVehicle(vehicleDto: VehicleDto): ApiResponse<VehicleDto> {
        try {
            if (vehicleRepository.vehicleExists(vehicleDto.sticker)) {
                return ApiResponse(
                    code = 409,
                    message = "Vehicle with sticker ${vehicleDto.sticker} already exists."
                )
            }
            val sticker = vehicleDto.sticker

            val save = vehicleRepository.save(vehicleDto.mapToModel(sticker))
            return if (!save) {
                ApiResponse(code = 201, data = vehicleDto.copy(sticker = sticker))
            } else ApiResponse(
                code = 500,
                message = "Failed to save to Db"
            )
        } catch (e: Exception) {
            System.err.println("Error creating vehicle: ${e.message}")
            e.printStackTrace()
            return ApiResponse(code = 500, message = "Failed to create vehicle: ${e.message}")
        }
    }

    suspend fun getVehicle(sticker: Int): ApiResponse<VehicleDto> {
        return try {
            vehicleRepository.getByStickerID(sticker)?.let { vehicleModel ->
                ApiResponse(
                    data = vehicleModel.mapToDto(),
                    message = "Vehicle Retrieved Successfully."
                )
            } ?: ApiResponse(
                code = 404,
                message = "Error retrieving sticker $sticker not found."
            )
        } catch (e: Exception) {
            System.err.println("Error retrieving vehicle with sticker $sticker: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to retrieve vehicle: ${e.message ?: "Unknown error"}"
            )
        }
    }

    suspend fun updateVehicle(
        sticker: Int,
        vehicleDto: VehicleDto
    ): ApiResponse<VehicleDto> {
        return try {
            if (!vehicleRepository.vehicleExists(sticker)) {
                return ApiResponse(
                    code = 404,
                    message = "Vehicle with sticker $sticker not found"
                )
            }
            if (sticker != vehicleDto.sticker) {
                return ApiResponse(
                    code = 400,
                    message = "Sticker in path ($sticker) does not match sticker in body (${vehicleDto.sticker}"
                )
            }
            val vehicleEntityToUpdate = vehicleDto.mapToModel(sticker)
            val update = vehicleRepository.update(vehicleEntityToUpdate)
            if (update) {
                ApiResponse(
                    data = vehicleDto,
                    message = "Vehicle with sticker $sticker update succesfully."
                )
            } else {
                ApiResponse(
                    code = 500,
                    message = "Failed to update vehicle with sticker $sticker due to an internal error."
                )
            }
        } catch (e: Exception) {
            System.err.println("Error updating vehicle with sticker $sticker: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to update vehicle: ${e.message ?: "Unknown error"}"
            )
        }
    }

    suspend fun deleteVehicle(sticker: Int): ApiResponse<Unit> {
        return try {
            if (!vehicleRepository.vehicleExists(sticker)) {
                return ApiResponse(code = 404, message = "Vehicle with sticker $sticker not found.")
            }
            val delete = vehicleRepository.delete(sticker)
            if (delete) {
                ApiResponse(message = "Vehicle with sticker $sticker deleted successfully.")
            } else {
                ApiResponse(code = 500, message = "Failed to delete vehicle with sticker $sticker due to an internal error.")
            }
        } catch (e: Exception) {
            System.err.println("Error deleting vehicle with sticker $sticker: ${e.message}")
            e.printStackTrace()
            ApiResponse(code = 500, message = "Failed to delete vehicle: ${e.message ?: "Unknown error"}")
        }
    }

}