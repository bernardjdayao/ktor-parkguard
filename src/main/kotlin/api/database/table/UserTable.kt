package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table(name="users"){
    val id = varchar("id", length = 100)
    val firstname = varchar("firstname", length = 50)
    val lastname = varchar("lastname", length = 50)
    val age = integer("age")
}