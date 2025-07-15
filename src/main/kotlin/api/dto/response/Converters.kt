package com.parkguard.api.dto.response

import com.parkguard.api.dto.requestdto.UserDto
import com.parkguard.api.model.UserModel

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