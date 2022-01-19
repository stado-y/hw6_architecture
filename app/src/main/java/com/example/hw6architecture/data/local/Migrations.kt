package com.example.hw6architecture.data.local

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@DeleteColumn(
    tableName = "actors_table",
    columnName = "character"
)
@DeleteColumn(
    tableName = "actors_table",
    columnName = "order"
)
@DeleteColumn(
    tableName = "actors_table",
    columnName = "movieId"
)
class Migrate_1_2 : AutoMigrationSpec