package com.parkguard.api.database

import com.parkguard.api.database.table.UserTable
import com.parkguard.api.database.table.VehicleTable
import com.parkguard.api.model.UserModel
import com.parkguard.api.model.VehicleModel
import org.jetbrains.exposed.sql.ResultRow

fun rowToModel(row: ResultRow) : UserModel{
    return UserModel(
        id= row[UserTable.id],
        firstname= row[UserTable.firstname],
        lastname= row[UserTable.lastname],
        age= row[UserTable.age]
    )
}

fun rowToModelVehicle(row: ResultRow) : VehicleModel{
    return VehicleModel(
        sticker = row[VehicleTable.sticker],
        plateNumber = row[VehicleTable.plateNumber],
        brand = row[VehicleTable.brand],
        model = row[VehicleTable.model],
        color = row[VehicleTable.color],
        year = row[VehicleTable.year],
        ownerId = row[VehicleTable.ownerId]
    )
}