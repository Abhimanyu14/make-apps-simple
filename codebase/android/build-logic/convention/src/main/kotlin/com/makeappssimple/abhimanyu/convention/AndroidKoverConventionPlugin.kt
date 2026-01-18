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

package com.makeappssimple.abhimanyu.convention

import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidKoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlinx.kover")

            extensions.configure<KoverProjectExtension> {
                currentProject {
                    instrumentation {
                        disabledForTestTasks.add("testReleaseUnitTest")
                    }
                }
                reports {
                    filters {
                        excludes {
                            packages(
                                "org.koin.ksp.generated",
                                "*.di.*",
                                "*.platform.*"
                            )
                            classes(
                                "*Dao_Impl",
                                "*_HiltModules*"
                            )
                        }
                    }
                }
            }
        }
    }
}
