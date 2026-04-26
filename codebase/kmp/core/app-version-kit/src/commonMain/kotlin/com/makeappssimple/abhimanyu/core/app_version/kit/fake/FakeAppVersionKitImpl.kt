package com.makeappssimple.abhimanyu.core.app_version.kit.fake

import com.makeappssimple.abhimanyu.core.app_version.kit.AppVersion
import com.makeappssimple.abhimanyu.core.app_version.kit.AppVersionKit

public class FakeAppVersionKitImpl : AppVersionKit {
    override fun getAppVersion(): AppVersion {
        return AppVersion(
            versionName = "1.2.3",
            versionNumber = 123L,
        )
    }
}
