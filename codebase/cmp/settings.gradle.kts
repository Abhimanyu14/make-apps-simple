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

rootProject.name = "make-apps-simple"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":cat-fact:android-app")
include(":cat-fact:desktop-app")
include(":cat-fact:shared")
include(":cat-fact:web-app")
include(":core:app-version")
include(":core:clipboard")
include(":core:coroutines")
include(":core:date-time")
include(":core:json")
include(":core:kotlin")
include(":core:log-kit")
include(":core:uri")
include(":cosmos-design-system")
include(":cosmos-design-system-catalog:android-app")
include(":cosmos-design-system-catalog:desktop-app")
include(":cosmos-design-system-catalog:shared")
include(":cosmos-design-system-catalog:web-app")
include(":core:build-config")
