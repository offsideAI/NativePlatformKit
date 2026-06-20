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

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * The text styles consumed by NativePlatformKit components. A deliberately small, semantic set —
 * components read [TextStyle]s from `NpkTheme.typography` rather than constructing their own.
 *
 * Instances are [Immutable].
 */
@Immutable
public class NpkTypography(
    /** Large, high-emphasis display text. */
    public val display: TextStyle,
    /** Section and screen titles. */
    public val title: TextStyle,
    /** Default body text. */
    public val body: TextStyle,
    /** Text used inside interactive controls such as buttons. */
    public val label: TextStyle,
    /** Small supporting/caption text. */
    public val caption: TextStyle,
) {
    /** Returns a copy overriding only the supplied styles. */
    public fun copy(
        display: TextStyle = this.display,
        title: TextStyle = this.title,
        body: TextStyle = this.body,
        label: TextStyle = this.label,
        caption: TextStyle = this.caption,
    ): NpkTypography = NpkTypography(display, title, body, label, caption)

    /**
     * Projects these styles onto a Material 3 [Typography] so wrapped Material content stays
     * visually consistent with NPK components.
     */
    public fun toMaterialTypography(): Typography {
        val base = Typography()
        return base.copy(
            displaySmall = display,
            titleLarge = title,
            bodyLarge = body,
            labelLarge = label,
            bodySmall = caption,
        )
    }

    public companion object {
        /** The default NativePlatformKit type scale. */
        public fun default(): NpkTypography =
            NpkTypography(
                display = TextStyle(fontSize = 36.sp, lineHeight = 44.sp, fontWeight = FontWeight.Bold),
                title = TextStyle(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.SemiBold),
                body = TextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Normal),
                label = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium),
                caption = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal),
            )
    }
}

/**
 * [androidx.compose.runtime.CompositionLocal] carrying the current [NpkTypography]. Prefer reading
 * via `NpkTheme.typography`.
 */
public val LocalNpkTypography: androidx.compose.runtime.ProvidableCompositionLocal<NpkTypography> =
    staticCompositionLocalOf { NpkTypography.default() }
