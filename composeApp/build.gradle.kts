import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                        open = false
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            api(libs.koin.core)
            implementation(libs.koin.compose.multiplatform)
            implementation(projects.shared)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json.wasm)
            implementation(libs.ktor.client.content.negotiation.wasm)
            implementation(libs.kotlinx.serialization)
        }
    }
}


