package com.parkguard.api.service

import com.parkguard.api.database.table.ParkingSlotTable
import com.parkguard.api.dto.request.ParkingSlotDto
import com.parkguard.api.dto.response.ApiResponse
import com.parkguard.api.dto.response.mapToDto
import com.parkguard.api.dto.response.mapToModel
import com.parkguard.api.model.ParkingSlotModel
import com.parkguard.api.repository.ParkingSlotRepository
import org.jetbrains.exposed.sql.AutoIncColumnType

class ParkingSlotService(private val parkingSlotRepository: ParkingSlotRepository) {
    suspend fun getAllParkingSlot(): ApiResponse<List<ParkingSlotDto>>{
        val list = parkingSlotRepository.getAll().map{
            it.mapToDto()
        }
        return ApiResponse(
            data=list,
            message = "Parking Slot retrieved succesfully"
        )
    }

    suspend fun getParkingInfo(row_code: String, parking_id: String): ApiResponse<List<ParkingSlotDto>> {
        val list = parkingSlotRepository.getParkingSlotInfo(row_code, parking_id)?.map{
            it.mapToDto()
        }
        return ApiResponse(
            data = list,
            message = "Parking Info retrieved succesfully"
        )
    }

    suspend fun getAllAvailableParkingSlots(parking_id:String): ApiResponse<Int>{
        return try {
            val count = parkingSlotRepository.getAllAvailableSlots(parking_id)
            ApiResponse(
                code = 200,
                message = "Successfully retrieved available slots $parking_id",
                data = count
            )
        } catch (e: Exception){
            ApiResponse(
                code = 500,
                message = "Failed to retrieve available slots: ${e.message}",
                data = null
            )
        }
    }

    suspend fun getParkedVehicle(sticker_no: String): ApiResponse<ParkingSlotDto> {
        return try {
            parkingSlotRepository.getParkedVehicle(sticker_no)?.let { parkingSlotModel ->
                ApiResponse(
                    code = 200,
                    message = "Parking Slot retrieved successfully",
                    data = parkingSlotModel.mapToDto()
                )
            } ?: ApiResponse(
                code = 404,
                message = "Vehicle with sticker number $sticker_no is not parked",
                data = null
            )
        } catch (e: Exception) {
            println("Error retrieving parked vehicle: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Error retrieving parked vehicle: ${e.message}",
                data = null
            )
        }
    }

    suspend fun isParked(sticker_no: String, parking_id: String): ApiResponse<Boolean>{
        return try{
            val result = parkingSlotRepository.isParked(sticker_no,parking_id)
            ApiResponse(
                code = 200,
                message = "Successfully retrieved if $sticker_no is parked",
                data = result
            )
        } catch (e: Exception){
            println("Error retrieving parked vehicle: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Error retrieving",
                data = null
            )
        }
    }

    suspend fun getByParkingSlot(slot_no: Int): ApiResponse<List<ParkingSlotDto>>{
        return try{
            val list = parkingSlotRepository.getBySlotNo(slot_no)?.map{it.mapToDto()}
            return ApiResponse(
                data=list,
                message = "Parking Slot retrieved succesfully"
            )
        } catch (e:Exception){
            System.err.println("Error retrieving parking slot $slot_no: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Failed to get parking slot: ${e.message ?: "Unknown error"}"
            )
        }
    }

    suspend fun putIntoParkingSlot(parkingSlotDto: ParkingSlotDto): ApiResponse<ParkingSlotDto>{
        val slot_num = parkingSlotDto.slot_no
        val put = parkingSlotRepository.putIntoParking(parkingSlotDto.mapToModel(slot_num))
        return if(put){
            ApiResponse(
                code = 201,
                data = parkingSlotDto.copy(slot_num)
            )
        }else ApiResponse(
            code = 500,
            message = "Failed to save to DB"
        )
    }

    suspend fun putCarIntoSlot(parkingSlotDto: ParkingSlotDto): ApiResponse<String> {
        val parking_id = parkingSlotDto.parking_id
        val row_code = parkingSlotDto.row_code
        val slot_no = parkingSlotDto.slot_no
        val stickerNo = parkingSlotDto.sticker_no ?: return ApiResponse(
            code = 400,
            message = "Sticker number is required"
        )

        return try {
            val carToBeParked =
                parkingSlotRepository.entryParking(stickerNo, slot_no, row_code, parking_id)
            if (carToBeParked) {
                ApiResponse(
                    message = "Parking slot $row_code$slot_no filled by $stickerNo",
                    data = "Sticker $stickerNo marked as parked"
                )
            } else {
                ApiResponse(
                    code = 500,
                    message = "Failed to update parking slot for sticker $stickerNo"
                )
            }
        } catch (e: Exception) {
            System.err.println("Error updating parking slot for $stickerNo: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Internal error: ${e.message ?: "Unknown error"}"
            )
        }
    }

    suspend fun updateParkingSlot(parkingSlotDto: ParkingSlotDto): ApiResponse<String> {
        val parking_id = parkingSlotDto.parking_id
        val row_code = parkingSlotDto.row_code
        val slot_no = parkingSlotDto.slot_no
        val stickerNo = parkingSlotDto.sticker_no ?: return ApiResponse(
            code = 400,
            message = "Sticker number is required"
        )

        return try {
            val isParked = parkingSlotRepository.isParked(stickerNo, parking_id)

            if (isParked) {
                val parking_id = parkingSlotDto.parking_id
                val row_code = parkingSlotDto.row_code
                val slot_no = parkingSlotDto.slot_no
                val updated = parkingSlotRepository.exitParking(slot_no, row_code, parking_id)
                if (updated) {
                    ApiResponse(
                        message = "Parking slot updated successfully: now available",
                        data = "Sticker $stickerNo marked as unparked"
                    )
                } else {

                    ApiResponse(
                        code = 500,
                        message = "Failed to update parking slot for sticker $stickerNo"
                    )
                }
            } else {
                putCarIntoSlot(parkingSlotDto)
            }
        } catch (e: Exception) {
            System.err.println("Error updating parking slot for $stickerNo: ${e.message}")
            e.printStackTrace()
            ApiResponse(
                code = 500,
                message = "Internal error: ${e.message ?: "Unknown error"}"
            )
        }
    }
}