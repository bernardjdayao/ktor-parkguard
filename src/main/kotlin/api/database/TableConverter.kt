package com.parkguard.api.database

import com.parkguard.api.database.table.LotRowTable
import com.parkguard.api.database.table.ParkingLotTable
import com.parkguard.api.database.table.ParkingSlotTable
import com.parkguard.api.database.table.UserTable
import com.parkguard.api.database.table.VehicleTable
import com.parkguard.api.model.LotRowModel
import com.parkguard.api.model.ParkingLotModel
import com.parkguard.api.model.ParkingSlotModel
import com.parkguard.api.model.UserModel
import com.parkguard.api.model.VehicleModel
import org.jetbrains.exposed.sql.ResultRow

fun rowToModel(row: ResultRow) : UserModel{
    return UserModel(
        id= row[UserTable.id],
        firstname= row[UserTable.firstname],
        lastname= row[UserTable.lastname],
        age= row[UserTable.age],
        year = row[UserTable.year],
        program = row[UserTable.program]
    )
}

fun rowToModelVehicle(row: ResultRow) : VehicleModel{
    return VehicleModel(
        sticker_no = row[VehicleTable.sticker_no],
        plate_number = row[VehicleTable.plate_number],
        brand = row[VehicleTable.brand],
        model = row[VehicleTable.model],
        color = row[VehicleTable.color],
        year = row[VehicleTable.year],
        id = row[VehicleTable.id]
    )
}

fun rowToModelParking(row: ResultRow) : ParkingLotModel{
    return ParkingLotModel(
        parking_id = row[ParkingLotTable.parking_id],
        parking_name = row[ParkingLotTable.parking_name]
    )
}

fun rowToModelLotRow(row: ResultRow) : LotRowModel{
    return LotRowModel(
        row_code = row[LotRowTable.row_code],
        parking_id = row[LotRowTable.parking_id]
    )
}

fun rowToModelParkingSlot(row: ResultRow) : ParkingSlotModel{
    return ParkingSlotModel(
        slot_no = row[ParkingSlotTable.slot_no],
        row_code = row[ParkingSlotTable.row_code],
        parking_id = row[ParkingSlotTable.parking_id],
        is_available = row[ParkingSlotTable.is_available],
        sticker_no = row[ParkingSlotTable.sticker_no]
    )
}