package com.parkguard.api.service

import com.parkguard.api.dto.requestdto.UserDto
import com.parkguard.api.dto.response.ApiResponse
import com.parkguard.api.dto.response.mapToDto
import com.parkguard.api.dto.response.mapToModel
import com.parkguard.api.repository.UserRepository
import java.util.UUID

class UserService(private val userRepository: UserRepository) {

    suspend fun getAllUser(): ApiResponse<List<UserDto>> {
        val list = userRepository.getAll().map {
            it.mapToDto()
        }
        return ApiResponse(data = list)
    }

    suspend fun createUser(userDto: UserDto): ApiResponse<UserDto> {
        val userId = UUID.randomUUID().toString()
        val save = userRepository.save(
            userDto mapToModel userId
        )
        return if (!save) {
            ApiResponse(code = 201, data = userDto.copy(id = userId))
        } else ApiResponse(
            code = 500,
            message = "Failed to save to Db"
        )
    }

    suspend fun getUser(userId: String?): ApiResponse<UserDto> {
        return userId?.let {
            userRepository.getById(it)?.let {
                ApiResponse(data = it.mapToDto())
            } ?: ApiResponse(code = 404, message = "User not found!")
        } ?: ApiResponse(code = 400, message = "Invalid User Id")

    }

    suspend fun updateUser(userId: String?, userDto: UserDto): ApiResponse<UserDto> {
        return userId?.let {
            val update = userRepository.update(userDto mapToModel userId)
            if (update) {
                ApiResponse(data = userDto.copy(id = userId))
            } else {
                ApiResponse(code = 500, message = "Saved to update User!")
            }
        } ?: ApiResponse(code = 400, message = "Invalid User Id")

    }

    suspend fun deleteUser(userId: String?): ApiResponse<String> {
        return userId?.let {
            val delete = userRepository.delete(it)
            if (delete) {
                ApiResponse(data = "Deleted Successfully")
            } else ApiResponse(code = 500, message = "Failed to delete user")
        } ?: ApiResponse(code = 400, message = "Invalid User Id")

    }
}