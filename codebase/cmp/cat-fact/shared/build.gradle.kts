import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.compose)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.multiplatform)
    alias(libs.plugins.plugin.kotlin.multiplatform.library)
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    androidLibrary {
       namespace = "com.makeappssimple.abhimanyu.catfact.shared"
       compileSdk = libs.versions.android.compile.sdk.get().toInt()
       minSdk = libs.versions.android.min.sdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }

       androidResources {
           enable = true
       }

       withHostTest {
           isIncludeAndroidResources = true
       }
    }

    explicitApi()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
        }

        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jsMain.dependencies {
            implementation(libs.kotlin.wrappers.browser)
        }
    }
}
