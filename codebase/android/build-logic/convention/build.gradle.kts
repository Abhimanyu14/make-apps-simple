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

plugins {
    `kotlin-dsl`
}

group = "com.makeappssimple.abhimanyu.convention"

dependencies {
    compileOnly("com.android.tools.build:gradle:8.10.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    compileOnly("org.jetbrains.kotlinx:kover-gradle-plugin:0.9.2")
    compileOnly("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.2.10-2.0.2")
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "makeappssimple.android.library"
            implementationClass =
                "com.makeappssimple.abhimanyu.convention.AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "makeappssimple.android.application"
            implementationClass =
                "com.makeappssimple.abhimanyu.convention.AndroidApplicationConventionPlugin"
        }
        register("androidKsp") {
            id = "makeappssimple.android.ksp"
            implementationClass =
                "com.makeappssimple.abhimanyu.convention.AndroidKspConventionPlugin"
        }
        register("androidKover") {
            id = "makeappssimple.android.kover"
            implementationClass =
                "com.makeappssimple.abhimanyu.convention.AndroidKoverConventionPlugin"
        }
    }
}
