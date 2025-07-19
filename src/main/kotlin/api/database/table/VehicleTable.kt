package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.Table

object VehicleTable: Table(name="vehicles") {
    val sticker = integer("sticker")
    val plateNumber = varchar("plateNumber", length = 10)
    val brand = varchar ("brand", length = 50)
    val model = varchar ("model", length = 50)
    val color = varchar ("color", length = 50)
    val year = integer ("year")
    var ownerId = varchar ("ownerId", length = 100)
}