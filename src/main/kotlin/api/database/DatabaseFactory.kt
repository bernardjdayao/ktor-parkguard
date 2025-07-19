package com.parkguard.api.database

import com.parkguard.api.database.table.UserTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private val DB_USER = System.getenv()["DB_USER"]?: ""
    private val DB_PASSWORD = System.getenv()["DB_PASSWORD"]?: ""
    fun init(){
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/parkguard",
            user = DB_USER,
            password = DB_PASSWORD
        )

        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    suspend fun <T> query (block : () -> T) : T = newSuspendedTransaction(Dispatchers.IO){
        block()
    }
}