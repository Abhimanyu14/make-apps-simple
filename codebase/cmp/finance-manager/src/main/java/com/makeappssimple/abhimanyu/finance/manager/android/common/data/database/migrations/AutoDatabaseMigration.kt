/*
 * Copyright 2025-2026 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.migrations

import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

/**
 * Room auto-migration
 * Source - https://developer.android.com/training/data-storage/room/migrating-db-versions#automigrationspec
 */
internal object AutoDatabaseMigration {
    @DeleteTable(
        tableName = "emoji_table",
    )
    class AutoMigration20to21 : AutoMigrationSpec

    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "source_id",
        toColumnName = "source_from_id",
    )
    class AutoMigration11to12 : AutoMigrationSpec

    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "sourceToId",
        toColumnName = "source_to_id",
    )
    class AutoMigration10to11 : AutoMigrationSpec

    @RenameColumn(
        tableName = "transaction_table",
        fromColumnName = "categoryId",
        toColumnName = "category_id",
    )
    class AutoMigration5to6 : AutoMigrationSpec

    @RenameColumn(
        tableName = "source_table",
        fromColumnName = "balanceAmount",
        toColumnName = "balance_amount",
    )
    class AutoMigration1to2 : AutoMigrationSpec
}
