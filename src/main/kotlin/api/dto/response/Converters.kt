package com.parkguard.api.dto.response

import com.parkguard.api.dto.request.VehicleDto
import com.parkguard.api.dto.requestdto.UserDto
import com.parkguard.api.model.UserModel
import com.parkguard.api.model.VehicleModel

infix fun UserDto.mapToModel(userId:String): UserModel {
    return UserModel(
        id=userId,
        firstname=this.firstname,
        lastname=this.lastname,
        age=this.age)
}

fun UserModel.mapToDto():UserDto{
    return UserDto(
        id=this.id,
        firstname=this.firstname,
        lastname=this.lastname,
        age=this.age
    )
}

fun VehicleDto.mapToModel(platenumber: String): VehicleModel{
    return VehicleModel(
        plateNumber = platenumber,
        brand = this.brand,
        model = this.model,
        color = this.color,
        year = this.year,
        sticker = this.sticker,
        ownerId = this.ownerId
    )
}

fun VehicleModel.mapToDto(): VehicleDto{
    return VehicleDto(
        plateNumber = this.plateNumber,
        brand = this.brand,
        model = this.model,
        color = this.color,
        year = this.year,
        sticker = this.sticker,
        ownerId = this.ownerId
    )
}