package pl.tamides.db

import pl.tamides.db.migrations.Migration1
import pl.tamides.db.tables.Test
import pl.tamides.db.tables.Test2
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import kotlin.reflect.KClass
import kotlin.system.exitProcess

class Database {
    var isInitialized = false
    private var statement: Statement? = null

    companion object {
        private const val DATABASE_URL = "jdbc:sqlite:database.db"

        val version = 1
        val tables = arrayListOf<KClass<*>>(
                Test::class,
                Test2::class
        )
        val migrations = arrayListOf(
                Migration1::class
        )

        @Volatile
        var instance: Database = Database()
            get() {
                if (!field.isInitialized) {
                    synchronized(Database::class.java) {
                        if (!field.isInitialized) {
                            field = Database(true)
                        }
                    }
                }
                return field
            }
            private set
    }

    private constructor()

    private constructor(isInitialized: Boolean) {
        if (!isInitialized) {
            return
        }

        this.isInitialized = true

        try {
            statement = DriverManager.getConnection(DATABASE_URL).createStatement()
        } catch (e: SQLException) {
            e.printStackTrace()
            exitProcess(0)
        }
    }
}