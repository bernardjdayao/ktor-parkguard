package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.Table

object ParkingSlotTable: Table("parkingslot") {
    val slot_no = integer("slot_no")
    val row_code = varchar("row_code", 10)
    val parking_id = varchar("parking_id", 10)
    val is_available = bool("is_available")
    val sticker_no = varchar("sticker_no", 10)
}