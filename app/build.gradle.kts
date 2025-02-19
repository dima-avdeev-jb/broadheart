@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR", "UnstableApiUsage", "OPT_IN_USAGE")
@file:OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("kmp-application-conventions")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
}

android {
    namespace = "io.sellmair.broadheart"
}

kotlin {
    sourceSets.commonMain.get().dependencies {
        api(project(":models"))
        api(project(":utils"))
        api(project(":bluetooth"))

        /* COMPOSE */
        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.runtime)

        implementation(compose.material3)
        implementation(compose.materialIconsExtended)

        /* Utils */
        implementation(Dependencies.coroutinesCore)
        implementation(Dependencies.okio)
    }

    sourceSets.androidMain.get().dependencies {
        /* androidx */
        implementation("androidx.activity:activity-compose:1.7.0-rc01")
        implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")

        /* Polar SDK and dependencies */
        implementation("com.github.polarofficial:polar-ble-sdk:4.0.0")
        implementation("io.reactivex.rxjava3:rxjava:3.1.5")
        implementation("io.reactivex.rxjava3:rxandroid:3.0.0")

        /* kotlinx */
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.6.4")

    }

    sourceSets.invokeWhenCreated("androidDebug") {
        dependencies {
            implementation("androidx.compose.ui:ui-tooling:1.3.3")
            implementation("androidx.compose.ui:ui-test-manifest:1.3.3")
        }
    }

    sourceSets.androidInstrumentedTest.get().dependencies {
        implementation("androidx.compose.ui:ui-test-junit4")
    }


    /* Setup frameworks for iOS */
    targets.withType<KotlinNativeTarget>().all {
        if (konanTarget.family.isAppleFamily) {


            binaries.framework {
                export(project(":models"))
                export(project(":bluetooth"))
                export(project(":utils"))

                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    "-Xdisable-phases=VerifyBitcode"
                )

                transitiveExport = true
                isStatic = true
            }
        }
    }
}

