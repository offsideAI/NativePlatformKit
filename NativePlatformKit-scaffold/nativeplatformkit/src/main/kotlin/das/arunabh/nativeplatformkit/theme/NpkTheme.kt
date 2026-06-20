/*
 * Copyright 2026 The NativePlatformKit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package das.arunabh.nativeplatformkit.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import das.arunabh.nativeplatformkit.token.LocalNpkColors
import das.arunabh.nativeplatformkit.token.LocalNpkElevation
import das.arunabh.nativeplatformkit.token.LocalNpkShapes
import das.arunabh.nativeplatformkit.token.LocalNpkSpacing
import das.arunabh.nativeplatformkit.token.LocalNpkTypography
import das.arunabh.nativeplatformkit.token.NpkColors
import das.arunabh.nativeplatformkit.token.NpkElevation
import das.arunabh.nativeplatformkit.token.NpkShapes
import das.arunabh.nativeplatformkit.token.NpkSpacing
import das.arunabh.nativeplatformkit.token.NpkTypography

/**
 * The root theme for NativePlatformKit. Wrap your app (or any subtree that uses NPK components) in
 * `NpkTheme { ... }` to provide the design tokens that every component reads.
 *
 * NPK tokens are exposed through [CompositionLocal]s and are accessible via the [NpkTheme] object,
 * e.g. `NpkTheme.colors.primary`. The theme also wraps a Material 3 [MaterialTheme] configured from
 * the same tokens, so Material components and plain `MaterialTheme.*` lookups stay visually
 * consistent with NPK components.
 *
 * Example:
 * ```
 * NpkTheme {
 *     NpkButton(onClick = { /* ... */ }) { Text("Click me") }
 * }
 * ```
 *
 * @param darkTheme whether to use the dark palette. Defaults to the system setting.
 * @param dynamicColor on Android 12+ (API 31), derive colors from the user's wallpaper. When `true`
 *   on a supported device, the wallpaper-based palette overrides [colors].
 * @param colors the color tokens to use. Defaults to [NpkColors.light] / [NpkColors.dark].
 * @param typography the type tokens to use.
 * @param shapes the shape tokens to use.
 * @param spacing the spacing tokens to use.
 * @param elevation the elevation tokens to use.
 * @param content the composable content themed by NPK.
 */
@Composable
public fun NpkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    colors: NpkColors = if (darkTheme) NpkColors.dark() else NpkColors.light(),
    typography: NpkTypography = NpkTypography.default(),
    shapes: NpkShapes = NpkShapes.default(),
    spacing: NpkSpacing = NpkSpacing.default(),
    elevation: NpkElevation = NpkElevation.default(),
    content: @Composable () -> Unit,
) {
    val resolvedColors: NpkColors =
        when {
            dynamicColor && supportsDynamicColor() -> {
                val context = LocalContext.current
                val scheme =
                    if (darkTheme) {
                        dynamicDarkColorScheme(context)
                    } else {
                        dynamicLightColorScheme(context)
                    }
                NpkColors.fromMaterialColorScheme(scheme, isDark = darkTheme)
            }

            else -> {
                colors
            }
        }

    CompositionLocalProvider(
        LocalNpkColors provides resolvedColors,
        LocalNpkTypography provides typography,
        LocalNpkShapes provides shapes,
        LocalNpkSpacing provides spacing,
        LocalNpkElevation provides elevation,
    ) {
        MaterialTheme(
            colorScheme = resolvedColors.toMaterialColorScheme(),
            typography = typography.toMaterialTypography(),
            shapes = shapes.toMaterialShapes(),
            content = content,
        )
    }
}

/** Whether the current device supports Material You dynamic color (Android 12 / API 31+). */
private fun supportsDynamicColor(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

/**
 * Accessors for the NativePlatformKit design tokens of the current [NpkTheme]. Read tokens through
 * this object from within composables, e.g. `NpkTheme.spacing.medium`.
 */
public object NpkTheme {
    /** The current color tokens. */
    public val colors: NpkColors
        @Composable
        @ReadOnlyComposable
        get() = LocalNpkColors.current

    /** The current typography tokens. */
    public val typography: NpkTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalNpkTypography.current

    /** The current shape tokens. */
    public val shapes: NpkShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalNpkShapes.current

    /** The current spacing tokens. */
    public val spacing: NpkSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalNpkSpacing.current

    /** The current elevation tokens. */
    public val elevation: NpkElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalNpkElevation.current
}
