package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.Table

object LotRowTable : Table("lotrow"){
    val row_code = varchar("row_code", 10)
    val parking_id = varchar("parking_id", 10)
}