package com.parkguard.api.dto.response

import com.parkguard.api.dto.request.LotRowDto
import com.parkguard.api.dto.request.ParkingLotDto
import com.parkguard.api.dto.request.ParkingSlotDto
import com.parkguard.api.dto.request.VehicleDto
import com.parkguard.api.dto.requestdto.UserDto
import com.parkguard.api.model.LotRowModel
import com.parkguard.api.model.ParkingLotModel
import com.parkguard.api.model.ParkingSlotModel
import com.parkguard.api.model.UserModel
import com.parkguard.api.model.VehicleModel

infix fun UserDto.mapToModel(userId: String): UserModel {
    return UserModel(
        id=userId,
        firstname=this.firstname,
        lastname=this.lastname,
        age=this.age,
        year=this.year,
        program=this.program
    )
}

fun UserModel.mapToDto():UserDto{
    return UserDto(
        id=this.id,
        firstname=this.firstname,
        lastname=this.lastname,
        age=this.age,
        year=this.year,
        program=this.program
    )
}

infix fun VehicleDto.mapToModel(sticker_no: String): VehicleModel{
    return VehicleModel(
        sticker_no= sticker_no,
        plate_number = this.plate_number,
        brand = this.brand,
        model = this.model,
        color = this.color,
        year = this.year,
        id = this.id
    )
}

fun VehicleModel.mapToDto(): VehicleDto{
    return VehicleDto(
        sticker_no = this.sticker_no,
        plate_number = this.plate_number,
        brand = this.brand,
        model = this.model,
        color = this.color,
        year = this.year,
        id = this.id
    )
}

fun ParkingLotDto.mapToModel(parking_id: String): ParkingLotModel{
    return ParkingLotModel(
        parking_id=parking_id,
        parking_name=this.parking_name
    )
}

fun ParkingLotModel.mapToDto(): ParkingLotDto{
    return ParkingLotDto(
        parking_id = this.parking_id,
        parking_name = this.parking_name
    )
}

fun LotRowDto.mapToModel(row_code: String): LotRowModel{
    return LotRowModel(
        row_code = row_code,
        parking_id = this.parking_id
    )
}

fun LotRowModel.mapToDto(): LotRowDto{
    return LotRowDto(
        row_code = this.row_code,
        parking_id = this.parking_id
    )
}

fun ParkingSlotDto.mapToModel(slot_no: Int): ParkingSlotModel{
    return ParkingSlotModel(
        slot_no = slot_no,
        row_code = this.row_code,
        parking_id = this.parking_id,
        is_available = this.is_available,
        sticker_no = this.sticker_no
    )
}



fun ParkingSlotModel.mapToDto(): ParkingSlotDto{
    return ParkingSlotDto(
        slot_no = this.slot_no,
        row_code = this.row_code,
        parking_id = this.parking_id,
        is_available = this.is_available,
        sticker_no = this.sticker_no
    )
}