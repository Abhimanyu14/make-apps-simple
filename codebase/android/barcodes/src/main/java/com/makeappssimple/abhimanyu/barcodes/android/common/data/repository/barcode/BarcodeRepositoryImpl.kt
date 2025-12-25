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

package com.makeappssimple.abhimanyu.barcodes.android.common.data.repository.barcode

import android.database.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.barcodes.android.common.data.barcode.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.common.data.mapper.BarcodeDataToDomainMapper
import com.makeappssimple.abhimanyu.barcodes.android.common.data.mapper.BarcodeDomainToDataMapper
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.common.domain.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BarcodeRepositoryImpl(
    private val barcodeDao: BarcodeDao,
    private val dispatcherProvider: DispatcherProvider,
    private val barcodeDataToDomainMapper: BarcodeDataToDomainMapper,
    private val barcodeDomainToDataMapper: BarcodeDomainToDataMapper,
) : BarcodeRepository {
    override suspend fun deleteBarcodes(
        vararg barcodes: BarcodeDomainModel,
    ): MyResult<Int> {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                val deletedBarcodesCount = barcodeDao.deleteBarcodes(
                    barcodeEntities = barcodes
                        .map(
                            transform = barcodeDomainToDataMapper::toDataModel,
                        )
                        .toTypedArray(),
                )
                MyResult.Success(
                    data = deletedBarcodesCount,
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            MyResult.Error(
                exception = sqLiteException,
            )
        }
    }

    override fun getAllBarcodesFlow(): Flow<List<BarcodeDomainModel>> {
        return barcodeDao.getAllBarcodesFlow().map {
            it.map(
                transform = barcodeDataToDomainMapper::toDomainModel,
            )
        }
    }

    override suspend fun getBarcodeById(
        id: Int,
    ): MyResult<BarcodeDomainModel?> {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                val barcodeDataModel = barcodeDao.getBarcodeById(
                    id = id,
                )
                MyResult.Success(
                    data = barcodeDataModel?.let {
                        barcodeDataToDomainMapper.toDomainModel(
                            barcodeDataModel = barcodeDataModel,
                        )
                    }
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            MyResult.Error(
                exception = sqLiteException,
            )
        }
    }

    override suspend fun insertBarcode(
        barcode: BarcodeDomainModel,
    ): MyResult<Long> {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                val barcodeId = barcodeDao.insertBarcode(
                    barcodeDomainToDataMapper.toDataModel(
                        barcodeDomainModel = barcode,
                    ),
                )
                MyResult.Success(
                    data = barcodeId,
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            MyResult.Error(
                exception = sqLiteException,
            )
        }
    }

    override suspend fun updateBarcodes(
        vararg barcodes: BarcodeDomainModel,
    ): MyResult<Int> {
        return try {
            dispatcherProvider.executeOnIoDispatcher {
                val updatedBarcodesCount = barcodeDao.updateBarcodes(
                    barcodeEntities = barcodes
                        .map(
                            transform = barcodeDomainToDataMapper::toDataModel,
                        )
                        .toTypedArray(),
                )
                MyResult.Success(
                    data = updatedBarcodesCount,
                )
            }
        } catch (
            sqLiteException: SQLiteException,
        ) {
            MyResult.Error(
                exception = sqLiteException,
            )
        }
    }
}
