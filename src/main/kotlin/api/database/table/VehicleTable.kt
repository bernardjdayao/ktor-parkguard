package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.Table

object VehicleTable : Table(name = "vehicles") {
    val sticker_no = varchar("sticker_no", 10)
    val plate_number = varchar("plate_number", 10)
    val brand = varchar("brand", 50)
    val model = varchar("model", 50)
    val color = varchar("color", 50)
    val year = integer("year")
    val id = varchar("id", 100)
}