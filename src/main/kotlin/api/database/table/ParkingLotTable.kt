package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.Table

object ParkingLotTable: Table("parkinglot") {
    val parking_id = varchar("parking_id", 10)
    val parking_name = varchar("parking_name", 20)
}