package com.parkguard.api.repository.impl

import com.parkguard.api.database.DatabaseFactory.query
import com.parkguard.api.database.rowToModel
import com.parkguard.api.database.table.UserTable
import com.parkguard.api.model.UserModel
import com.parkguard.api.repository.UserRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl : UserRepository {
    override suspend fun getAll(): List<UserModel> =
        query {
            UserTable.selectAll().map {
                rowToModel(it)
            }
        }

    override suspend fun getById(userId: String): UserModel? =
        query {
            UserTable.selectAll().where { UserTable.id eq userId }.map(::rowToModel).singleOrNull()
        }


    override suspend fun save(userModel: UserModel): Boolean {
        return query{
            UserTable.insert {
                it[id] = userModel.id
                it[firstname] = userModel.firstname
                it[lastname] = userModel.lastname
                it[age] = userModel.age
            }.insertedCount > 0
        }
    }

    override suspend fun update(userModel: UserModel): Boolean =
        query {
            UserTable.update ({ UserTable.id eq userModel.id}) {
                it[firstname] = userModel.firstname
                it[lastname] = userModel.lastname
                it[age] = userModel.age
            } > 0
        }


    override suspend fun delete(userId: String): Boolean =
        query{
            UserTable.deleteWhere {
                id eq userId
            } > 0
        }


    override suspend fun userExists(userId: String): Boolean =
        query {
            UserTable.select(UserTable.id).where{
                UserTable.id eq userId
            }.count()>0
        }
}