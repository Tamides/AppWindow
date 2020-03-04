package pl.tamides

import pl.tamides.db.Database
import pl.tamides.db.DatabaseUtils
import pl.tamides.db.migrations.Migration1
import pl.tamides.db.tables.Test
import java.awt.Toolkit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println(Toolkit.getDefaultToolkit().screenSize.toString())

        var create = "CREATE TABLE ${Test().javaClass.simpleName} ("

        for (i in Test().javaClass.declaredFields.indices) {
            val field = Test().javaClass.declaredFields[i]
            val type = when {
                field.type.simpleName.contains("String") -> "TEXT NOT NULL"
                field.type.simpleName.contains("int") -> "INTEGER NOT NULL"
                field.type.simpleName.contains("double") -> "NUMERIC NOT NULL"
                else -> ""
            }
            create += "${field.name} ${type}${if (i == (Test().javaClass.declaredFields.size - 1)) ");" else ", "}"
        }

        val aaa = Test::class.constructors.toMutableList()[0].call()

        val aaaa = Database.instance
        val abc = DatabaseUtils.getCreateTableQuery(Test::class)
        val ccc = Migration1().queries

        if (create == null) {
            create = create
        }
    }
}