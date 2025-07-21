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

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.plugin.android.application) apply false
    alias(libs.plugins.plugin.android.library) apply false
    alias(libs.plugins.plugin.detekt)
    alias(libs.plugins.plugin.dokka)
    alias(libs.plugins.plugin.kotlin.android) apply false
    alias(libs.plugins.plugin.kotlin.compose) apply false
    alias(libs.plugins.plugin.kotlinx.kover)
    alias(libs.plugins.plugin.ksp) apply false
    alias(libs.plugins.plugin.maven.publish) apply false
}

// region Detekt
detekt {
    toolVersion = "1.23.8"
    config.setFrom("$projectDir/config/detekt./detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(true)
        sarif.required.set(false)
        md.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
// endregion

kover {
    currentProject {
        instrumentation {
            disabledForTestTasks.add("testReleaseUnitTest")
        }
    }
}
