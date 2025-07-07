package com.makeappssimple.abhimanyu.barcodes.android.core.common.appversion.fake

import com.makeappssimple.abhimanyu.barcodes.android.core.common.appversion.AppVersion
import com.makeappssimple.abhimanyu.barcodes.android.core.common.appversion.AppVersionKit

internal class FakeAppVersionKitImpl : AppVersionKit {
    override fun getAppVersion(): AppVersion {
        return AppVersion(
            versionName = "versionName",
            versionNumber = 12L,
        )
    }
}
