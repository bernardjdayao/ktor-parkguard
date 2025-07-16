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
            if (vehicleRepository.vehicleExists(vehicleDto.plateNumber)) {
                return ApiResponse(
                    code = 409,
                    message = "Vehicle with plate number ${vehicleDto.plateNumber} already exists."
                )
            }
            val plateNumber = vehicleDto.plateNumber

            val save = vehicleRepository.save(vehicleDto.mapToModel(plateNumber))
            return if (!save) {
                ApiResponse(code = 201, data = vehicleDto.copy(plateNumber = plateNumber))
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

    suspend fun getVehicle(plateNumber: String): ApiResponse<VehicleDto> {
        return try {
            vehicleRepository.getByPlateNumber(plateNumber)?.let { vehicleModel ->
                ApiResponse(
                    data = vehicleModel.mapToDto(),
                    message = "Vehicle Retrieved Successfully."
                )
            } ?: ApiResponse(
                code = 404,
                message = "Error retrieving plate number $plateNumber not found."
            )
        } catch (e: Exception) {
            System.err.println("Error retrieving vehicle with plate number $plateNumber: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to retrieve vehicle: ${e.message ?: "Unknown error"}"
            )
        }
    }

    suspend fun updateVehicle(
        plateNumber: String,
        vehicleDto: VehicleDto
    ): ApiResponse<VehicleDto> {
        return try {
            if (!vehicleRepository.vehicleExists(plateNumber)) {
                return ApiResponse(
                    code = 404,
                    message = "Vehicle with plate number $plateNumber not found"
                )
            }
            if (plateNumber != vehicleDto.plateNumber) {
                return ApiResponse(
                    code = 400,
                    message = "Plate number in path ($plateNumber) does not match plate number in body (${vehicleDto.plateNumber}"
                )
            }
            val vehicleEntityToUpdate = vehicleDto.mapToModel(plateNumber)
            val update = vehicleRepository.update(vehicleEntityToUpdate)
            if (update) {
                ApiResponse(
                    data = vehicleDto,
                    message = "Vehicle with plate number $plateNumber update succesfully."
                )
            } else {
                ApiResponse(
                    code = 500,
                    message = "Failed to update vehicle with plate number $plateNumber due to an internal error."
                )
            }
        } catch (e: Exception) {
            System.err.println("Error updating vehicle with plate number $plateNumber: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to update vehicle: ${e.message ?: "Unknown error"}"
            )
        }
    }

    suspend fun deleteVehicle(plateNumber: String): ApiResponse<Unit> {
        return try {
            if (!vehicleRepository.vehicleExists(plateNumber)) {
                return ApiResponse(code = 404, message = "Vehicle with plate number $plateNumber not found.")
            }
            val delete = vehicleRepository.delete(plateNumber)
            if (delete) {
                ApiResponse(message = "Vehicle with plate number $plateNumber deleted successfully.")
            } else {
                ApiResponse(code = 500, message = "Failed to delete vehicle with plate number $plateNumber due to an internal error.")
            }
        } catch (e: Exception) {
            System.err.println("Error deleting vehicle with plate number $plateNumber: ${e.message}")
            e.printStackTrace()
            ApiResponse(code = 500, message = "Failed to delete vehicle: ${e.message ?: "Unknown error"}")
        }
    }

}