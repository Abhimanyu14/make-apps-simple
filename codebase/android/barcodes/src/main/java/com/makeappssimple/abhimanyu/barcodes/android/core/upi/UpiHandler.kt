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

package com.makeappssimple.abhimanyu.barcodes.android.core.upi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import java.util.Locale

// TODO(Abhi) - New Feature - UPI Payment
internal fun startPayment(
    barcodeValue: String,
    upiLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
) {
    getUpiIntentUri(
        amount = 1.0,
        uriString = barcodeValue,
    )?.let { uri ->
        // Comment line - if you want to open specific application then you can pass that package name
        // For example if you want to open Bhim app then pass Bhim app package name
        // val packageManager: PackageManager = packageManager
        // Intent intent = packageManager.getLaunchIntentForPackage("com.mgs.induspsp")

        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            this.data = uri
        }
        val chooser = Intent.createChooser(intent, "Pay with...")
        upiLauncher.launch(chooser)
    }
}

@Composable
internal fun rememberUpiLauncher(context: Context): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "Payment Processed", Toast.LENGTH_SHORT)
                .show()
            if (it.data == null) {
                Toast.makeText(context, "No Data Returned", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val response = it.data?.getStringExtra("response")
                var paymentCancel = ""
                var status = ""
                var approvalRefNo = ""
                var txnRef = ""
                var txnId = ""
                response?.split('&')?.forEach { res ->
                    val equalStr = res.split('=').toTypedArray()
                    if (equalStr.size >= 2) {
                        if (equalStr[0].equals("Status", ignoreCase = true)) {
                            status = equalStr[1].lowercase(Locale.getDefault())
                        }
                        if (equalStr[0].equals(
                                "ApprovalRefNo",
                                ignoreCase = true
                            )
                        ) {
                            approvalRefNo = equalStr[1]
                        }
                        if (equalStr[0].equals("txnRef", ignoreCase = true)) {
                            txnRef = equalStr[1]
                        }
                        if (equalStr[0].equals("txnId", ignoreCase = true)) {
                            txnId = equalStr[1]
                        }
                    } else {
                        paymentCancel = "Payment cancelled by user."
                    }
                }
                if (status.equals("success", ignoreCase = true)) {
                    // Code to handle successful transaction here.
                    Toast.makeText(
                        context,
                        "Payment Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("UPI", "txnId: $txnId")
                    Log.e("UPI", "responseStr: $approvalRefNo")
                    Log.e("UPI", "txnRef: $txnRef")
                } else if (paymentCancel.equals(
                        "Payment cancelled by user.",
                        ignoreCase = true
                    )
                ) {
                    Toast.makeText(
                        context,
                        "Payment cancelled by user.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Transaction failed.Please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show()
        }
    }
}

internal fun getUpiIntentUri(
    amount: Double,
    uriString: String,
): Uri? {
    val upiParams = extractUpiIntentParameters(
        uriString = uriString,
    )

    // mandatory
    val payeeAddress = upiParams["pa"]
    val payeeName = upiParams["pn"]
    val mode = upiParams["mode"] // ?: "00" // Transaction initiation mode
    val sign =
        upiParams["sign"] // Base 64 encoded Digital signature needs to be passed in this tag
    val orgid = upiParams["orgid"] ?: "000000"

    // optional
    val merchantCode = upiParams["mc"]
    val transactionId = upiParams["tid"]
    val transactionReferenceId = upiParams["tr"]
    val transactionNote = upiParams["tn"]
    val payeeAmount = upiParams["am"] // ?: "%.2f".format(amount)
    val minimumAmount = upiParams["mam"]
    val currencyCode = upiParams["cu"] ?: "INR"
    val url = upiParams["url"]
    val merchantId = upiParams["mid"]
    val merchantStoreId = upiParams["msid"]
    val merchantTerminalId = upiParams["mtid"]

    val referenceUrl = upiParams["refUrl"]
    val purpose = upiParams["purpose"]

    val requiredParams = listOf("pa", "pn")
    requiredParams.forEach {
        if (upiParams[it] == null) {
            return null
        }
    }

    val upiStringBuilder = StringBuilder()
    val upiPrefix = "upi://pay?"
    upiStringBuilder.append(upiPrefix)

    payeeAddress?.let {
        upiStringBuilder.append("pa=$it")
    }
    payeeName?.let {
        upiStringBuilder.append("&pn=$it")
    }
    mode?.let {
        upiStringBuilder.append("&mode=$it")
    }
    sign?.let {
        upiStringBuilder.append("&sign=$it")
    }
    orgid?.let {
        upiStringBuilder.append("&orgid=$it")
    }

    merchantCode?.let {
        upiStringBuilder.append("&mc=$it")
    }
    transactionId?.let {
        upiStringBuilder.append("&tid=$it")
    }
    transactionReferenceId?.let {
        upiStringBuilder.append("&tr=$it")
    }
    transactionNote?.let {
        upiStringBuilder.append("&tn=$it")
    }
    payeeAmount?.let {
        upiStringBuilder.append("&am=$it")
    }
    minimumAmount?.let {
        upiStringBuilder.append("&mam=$it")
    }
    currencyCode?.let {
        upiStringBuilder.append("&cu=$it")
    }
    url?.let {
        upiStringBuilder.append("&url=$it")
    }
    merchantId?.let {
        upiStringBuilder.append("&mid=$it")
    }
    merchantStoreId?.let {
        upiStringBuilder.append("&msid=$it")
    }
    merchantTerminalId?.let {
        upiStringBuilder.append("&mtid=$it")
    }
    referenceUrl?.let {
        upiStringBuilder.append("&refUrl=$it")
    }
    purpose?.let {
        upiStringBuilder.append("&purpose=$it")
    }

    val upi = upiStringBuilder.toString()
        .replace(" ", "+")
    Log.e("UPI", "UPI String - $upi")
    return Uri.parse(upi)
}

private fun extractUpiIntentParameters(
    uriString: String,
): Map<String, String> {
    val resultMap = mutableMapOf<String, String>()
    val upiPrefix = "upi://pay?"
    uriString
        .substring(upiPrefix.length)
        .split('&')
        .forEach { substring ->
            substring.split('=').let {
                if (it.size == 2) {
                    resultMap[it[0]] = it[1]
                }
            }
        }
    return resultMap
}
