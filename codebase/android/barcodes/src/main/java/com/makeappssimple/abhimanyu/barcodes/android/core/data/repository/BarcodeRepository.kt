package com.makeappssimple.abhimanyu.barcodes.android.core.data.repository

import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import kotlinx.coroutines.flow.Flow

/**
 * Responsibilities
 * 1. To handle SQL exceptions
 * 2. Make the calls thread safe
 *
 * Project convention
 * Method ordering - Create, Read, Update and Delete
 */
public interface BarcodeRepository {
    /**
     * @return Row id of inserted rows. First valid row id is 1.
     * Returns empty array if failed to insert.
     */
    public suspend fun insertBarcodes(
        vararg barcodes: Barcode,
    ): LongArray

    public fun getAllBarcodesFlow(): Flow<List<Barcode>>

    /**
     * @param id Required barcode id
     * @return Barcode with given [id] or returns null if no barcode has the given id.
     */
    public suspend fun getBarcode(
        id: Int,
    ): Barcode?

    /**
     * Only updates the existing rows using the primary key
     *
     * @return Number of rows updated
     */
    public suspend fun updateBarcodes(
        vararg barcodes: Barcode,
    ): Int

    /**
     * @return Number of rows deleted
     */
    public suspend fun deleteBarcodes(
        vararg barcodes: Barcode,
    ): Int
}
