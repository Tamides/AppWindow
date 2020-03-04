package pl.tamides.db

import kotlin.reflect.KClass
import kotlin.system.exitProcess

class DatabaseUtils {
    companion object {
        fun getCreateTableQuery(tableKClass: KClass<*>): String {
            val tableClass: Class<Any> = tableKClass.constructors.toMutableList()[0].call().javaClass
            var createTableQuery = "CREATE TABLE ${tableClass.simpleName} ("
            val fields = tableClass.declaredFields

            for (i in fields.indices) {
                val lastElement = i == (fields.size - 1)
                val endLine = if (lastElement) ");" else ", "
                val field = fields[i]
                val type = when (field.type.simpleName) {
                    "String" -> "TEXT"
                    "int" -> "INTEGER"
                    "double" -> "NUMERIC"
                    else -> exitProcess(0)
                }
                createTableQuery += "${field.name} ${type} NOT NULL${endLine}"
            }
            return createTableQuery
        }
    }
}