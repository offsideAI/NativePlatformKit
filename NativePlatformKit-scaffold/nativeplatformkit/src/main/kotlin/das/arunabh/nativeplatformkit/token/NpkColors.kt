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
package das.arunabh.nativeplatformkit.token

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * The semantic color roles consumed by NativePlatformKit components.
 *
 * These roles map onto Material 3's [ColorScheme] (see [toMaterialColorScheme]) but are kept as a
 * first-class NPK token so the design system can evolve independently of Material. Components must
 * read colors from [das.arunabh.nativeplatformkit.theme.NpkTheme.colors] rather than hard-coding
 * [Color] values.
 *
 * Instances are [Immutable]: construct a new instance to change colors rather than mutating one.
 */
@Immutable
public class NpkColors(
    /** Primary brand color, used for prominent actions such as filled buttons. */
    public val primary: Color,
    /** Content color (text/icons) drawn on top of [primary]. */
    public val onPrimary: Color,
    /** A tonal container derived from [primary], used for less prominent emphasis. */
    public val primaryContainer: Color,
    /** Content color drawn on top of [primaryContainer]. */
    public val onPrimaryContainer: Color,
    /** Secondary accent color. */
    public val secondary: Color,
    /** Content color drawn on top of [secondary]. */
    public val onSecondary: Color,
    /** The background behind scrollable content. */
    public val background: Color,
    /** Content color drawn on top of [background]. */
    public val onBackground: Color,
    /** The color of surfaces such as cards and sheets. */
    public val surface: Color,
    /** Content color drawn on top of [surface]. */
    public val onSurface: Color,
    /** A variant surface color used for subtle differentiation. */
    public val surfaceVariant: Color,
    /** Content color drawn on top of [surfaceVariant]. */
    public val onSurfaceVariant: Color,
    /** The color used for borders and dividers. */
    public val outline: Color,
    /** The color that signals errors. */
    public val error: Color,
    /** Content color drawn on top of [error]. */
    public val onError: Color,
    /** Whether this palette is a dark theme. Affects derived Material elevation overlays. */
    public val isDark: Boolean,
) {
    /**
     * Returns a copy of these colors, overriding only the roles explicitly supplied.
     */
    public fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        primaryContainer: Color = this.primaryContainer,
        onPrimaryContainer: Color = this.onPrimaryContainer,
        secondary: Color = this.secondary,
        onSecondary: Color = this.onSecondary,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        surface: Color = this.surface,
        onSurface: Color = this.onSurface,
        surfaceVariant: Color = this.surfaceVariant,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        outline: Color = this.outline,
        error: Color = this.error,
        onError: Color = this.onError,
        isDark: Boolean = this.isDark,
    ): NpkColors =
        NpkColors(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            error = error,
            onError = onError,
            isDark = isDark,
        )

    /**
     * Projects these NPK color roles onto a Material 3 [ColorScheme] so that wrapped
     * `MaterialTheme` content and Material components render consistently with NPK components.
     */
    public fun toMaterialColorScheme(): ColorScheme {
        val base = if (isDark) darkColorScheme() else lightColorScheme()
        return base.copy(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            secondary = secondary,
            onSecondary = onSecondary,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            error = error,
            onError = onError,
        )
    }

    public companion object {
        /** The default NativePlatformKit light color palette. */
        public fun light(): NpkColors =
            NpkColors(
                primary = Color(0xFF4C5BD4),
                onPrimary = Color(0xFFFFFFFF),
                primaryContainer = Color(0xFFDFE0FF),
                onPrimaryContainer = Color(0xFF00105C),
                secondary = Color(0xFF5B5D72),
                onSecondary = Color(0xFFFFFFFF),
                background = Color(0xFFFBF8FF),
                onBackground = Color(0xFF1B1B21),
                surface = Color(0xFFFBF8FF),
                onSurface = Color(0xFF1B1B21),
                surfaceVariant = Color(0xFFE3E1EC),
                onSurfaceVariant = Color(0xFF45464F),
                outline = Color(0xFF767680),
                error = Color(0xFFBA1A1A),
                onError = Color(0xFFFFFFFF),
                isDark = false,
            )

        /** The default NativePlatformKit dark color palette. */
        public fun dark(): NpkColors =
            NpkColors(
                primary = Color(0xFFBCC2FF),
                onPrimary = Color(0xFF1A2691),
                primaryContainer = Color(0xFF333FAB),
                onPrimaryContainer = Color(0xFFDFE0FF),
                secondary = Color(0xFFC4C5DD),
                onSecondary = Color(0xFF2D2F42),
                background = Color(0xFF131318),
                onBackground = Color(0xFFE4E1E9),
                surface = Color(0xFF131318),
                onSurface = Color(0xFFE4E1E9),
                surfaceVariant = Color(0xFF45464F),
                onSurfaceVariant = Color(0xFFC6C5D0),
                outline = Color(0xFF90909A),
                error = Color(0xFFFFB4AB),
                onError = Color(0xFF690005),
                isDark = true,
            )

        /**
         * Builds NPK colors from an existing Material 3 [ColorScheme] — useful when adopting
         * dynamic color or an app's own Material theme.
         */
        public fun fromMaterialColorScheme(
            scheme: ColorScheme,
            isDark: Boolean,
        ): NpkColors =
            NpkColors(
                primary = scheme.primary,
                onPrimary = scheme.onPrimary,
                primaryContainer = scheme.primaryContainer,
                onPrimaryContainer = scheme.onPrimaryContainer,
                secondary = scheme.secondary,
                onSecondary = scheme.onSecondary,
                background = scheme.background,
                onBackground = scheme.onBackground,
                surface = scheme.surface,
                onSurface = scheme.onSurface,
                surfaceVariant = scheme.surfaceVariant,
                onSurfaceVariant = scheme.onSurfaceVariant,
                outline = scheme.outline,
                error = scheme.error,
                onError = scheme.onError,
                isDark = isDark,
            )
    }
}

/**
 * [androidx.compose.runtime.CompositionLocal] carrying the current [NpkColors]. Prefer reading via
 * `NpkTheme.colors`. Defaults to [NpkColors.light] so previews and tests that forget to wrap in
 * `NpkTheme` still render sensibly.
 */
public val LocalNpkColors: androidx.compose.runtime.ProvidableCompositionLocal<NpkColors> =
    staticCompositionLocalOf { NpkColors.light() }
