package com.parkguard.api.repository

import com.parkguard.api.model.UserModel

interface UserRepository {
    suspend fun getAll():List<UserModel>

    suspend fun getById(userId:String):UserModel?

    suspend fun save(userModel:UserModel): Boolean

    suspend fun update(userModel:UserModel): Boolean

    suspend fun delete(userId:String): Boolean

    suspend fun userExists(userId: String): Boolean

}