package pl.tamides.db.migrations

import pl.tamides.db.Database
import pl.tamides.db.DatabaseUtils

class Migration1 {
    val queries = Database.tables.map { DatabaseUtils.getCreateTableQuery(it) }
}