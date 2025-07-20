/*
 * Copyright 2025-2025 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.core.data.repository

import android.database.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.barcodes.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.BarcodeEntity
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.asEntity
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

internal class BarcodeRepositoryImpl(
    private val barcodeDao: BarcodeDao,
    private val dispatcherProvider: DispatcherProvider,
) : BarcodeRepository {
    override suspend fun deleteBarcodes(
        vararg barcodes: Barcode,
    ): Int {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                barcodeDao.deleteBarcodes(
                    barcodeEntities = barcodes.map(Barcode::asEntity)
                        .toTypedArray(),
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            // TODO(Abhi): Handle error when SQL query fails
            sqLiteException.printStackTrace()
            0
        }
    }

    override fun getAllBarcodesFlow(): Flow<List<Barcode>> {
        return try {
            barcodeDao.getAllBarcodesFlow().map {
                it.map(BarcodeEntity::asExternalModel)
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            // TODO(Abhi): Handle error when SQL query fails
            sqLiteException.printStackTrace()
            emptyFlow()
        }
    }

    override suspend fun getBarcode(
        id: Int,
    ): Barcode? {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                barcodeDao.getBarcode(
                    id = id,
                )?.asExternalModel()
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            // TODO(Abhi): Handle error when SQL query fails
            sqLiteException.printStackTrace()
            null
        }
    }

    override suspend fun insertBarcodes(
        vararg barcodes: Barcode,
    ): LongArray {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                barcodeDao.insertBarcodes(
                    barcodeEntities = barcodes.map(Barcode::asEntity)
                        .toTypedArray(),
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            // TODO(Abhi): Handle error when SQL query fails
            sqLiteException.printStackTrace()
            longArrayOf()
        }
    }

    override suspend fun updateBarcodes(
        vararg barcodes: Barcode,
    ): Int {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                barcodeDao.updateBarcodes(
                    barcodeEntities = barcodes.map(Barcode::asEntity)
                        .toTypedArray(),
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            // TODO(Abhi): Handle error when SQL query fails
            sqLiteException.printStackTrace()
            0
        }
    }
}
