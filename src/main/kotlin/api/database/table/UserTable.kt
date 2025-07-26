package com.parkguard.api.database.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table(name="users"){
    val id = varchar("id", 100)
    val firstname = varchar("firstname", 50)
    val lastname = varchar("lastname", 50)
    val age = integer("age")
    val year = varchar("year", 50)
    val program = varchar("program", 50)
}