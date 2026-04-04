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

@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.detekt)
    alias(libs.plugins.plugin.dokka)
    alias(libs.plugins.plugin.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.plugin.screenshot)
    alias(libs.plugins.plugin.maven.publish)
}

android {
    namespace = "com.makeappssimple.abhimanyu.library.cosmos.design.system.android"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()
    resourcePrefix = "cosmos"

    // Screenshot testing
    experimentalProperties["android.experimental.enableScreenshotTest"] = true

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("consumer-rules.pro")
    }

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        disable += "AndroidGradlePluginVersion"
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.makeappssimple.abhimanyu.cosmos.design.system.resources"
}

dependencies {
    detektPlugins(libs.bundles.detekt)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
    screenshotTestImplementation(libs.screenshot.validation.api)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        publishAllLibraryVariants()
    }
    iosArm64()
    iosSimulatorArm64()
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    js(IR) {
        browser()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core-date-time"))
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.runtime)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.components.resources)
                implementation(libs.kotlinx.collections.immutable)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.kotlin)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.compose.foundation)
                implementation(libs.androidx.compose.material3)
                implementation(libs.androidx.compose.runtime)
                implementation(libs.androidx.core.ktx)
            }
        }
        val androidDebug by creating {
            dependsOn(androidMain)
            dependencies {
                implementation(libs.test.compose.ui.manifest)
                implementation(libs.androidx.compose.ui.tooling)
                implementation(libs.androidx.compose.ui.tooling.preview)
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.test.compose.ui.junit4)
                implementation(libs.test.room)
                implementation(libs.bundles.koin.test)
                implementation(libs.bundles.test)
            }
        }
    }

    explicitApi()

    compilerOptions {
        // Common compiler options go here
    }
}

mavenPublishing {
    configure(
        AndroidSingleVariantLibrary(
            publishJavadocJar = true,
            sourcesJar = true,
            variant = "release",
        )
    )

    coordinates(
        groupId = "io.github.abhimanyu14",
        artifactId = "cosmos-design-system",
        version = libs.versions.app.cosmos.design.system.catalog.version.name.get()
    )

    pom {
        name.set("Cosmos Design System")
        description.set("Cosmos Design System")
        inceptionYear.set("2025")
        url.set("https://github.com/Abhimanyu14/cosmos-design-system")

        developers {
            developer {
                id.set("Abhimanyu14")
                name.set("Abhimanyu")
                email.set("abhimanyu.n14@gmail.com")
            }
        }

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        scm {
            url.set("https://github.com/Abhimanyu14/cosmos-design-system")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
