package com.baka3k.core.database

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

class DatabaseMigrations {

    @RenameColumn(
        tableName = "topics",
        fromColumnName = "description",
        toColumnName = "shortDescription"
    )
    class Schema2to3 : AutoMigrationSpec
}
