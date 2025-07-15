package com.parkguard.api.repository.impl

import com.parkguard.api.model.UserModel
import com.parkguard.api.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    val userMap = mutableMapOf<String, UserModel>()
    override suspend fun getAll(): List<UserModel> {
        return userMap.values.toList()
    }

    override suspend fun getById(userId: String): UserModel? {
        return userMap.get(userId)
    }

    override suspend fun save(userModel: UserModel): Boolean {
        val result = userMap.put(userModel.id, userModel)
        return result != null
    }

    override suspend fun update(userModel: UserModel): Boolean {
        val result = userMap.replace(userModel.id, userModel)
        return result != null
    }

    override suspend fun delete(userId: String): Boolean {
        val result = userMap.remove(userId)
        return result != null
    }

    override suspend fun userExists(userId: String): Boolean {
        return userMap.containsKey(userId)
    }
}