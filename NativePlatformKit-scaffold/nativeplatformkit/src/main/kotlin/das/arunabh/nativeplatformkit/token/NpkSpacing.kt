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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The spacing scale (an 8dp baseline grid) consumed by NativePlatformKit components for padding,
 * gaps, and insets. Components read spacing from `NpkTheme.spacing` rather than hard-coding [Dp]s.
 *
 * Instances are [Immutable].
 */
@Immutable
public class NpkSpacing(
    /** No space (0dp). */
    public val none: Dp,
    /** Extra-small space (4dp). */
    public val xs: Dp,
    /** Small space (8dp). */
    public val small: Dp,
    /** Medium space (16dp) — the most common default. */
    public val medium: Dp,
    /** Large space (24dp). */
    public val large: Dp,
    /** Extra-large space (32dp). */
    public val xl: Dp,
) {
    /** Returns a copy overriding only the supplied values. */
    public fun copy(
        none: Dp = this.none,
        xs: Dp = this.xs,
        small: Dp = this.small,
        medium: Dp = this.medium,
        large: Dp = this.large,
        xl: Dp = this.xl,
    ): NpkSpacing = NpkSpacing(none, xs, small, medium, large, xl)

    public companion object {
        /** The default NativePlatformKit spacing scale. */
        public fun default(): NpkSpacing =
            NpkSpacing(
                none = 0.dp,
                xs = 4.dp,
                small = 8.dp,
                medium = 16.dp,
                large = 24.dp,
                xl = 32.dp,
            )
    }
}

/**
 * [androidx.compose.runtime.CompositionLocal] carrying the current [NpkSpacing]. Prefer reading via
 * `NpkTheme.spacing`.
 */
public val LocalNpkSpacing: androidx.compose.runtime.ProvidableCompositionLocal<NpkSpacing> =
    staticCompositionLocalOf { NpkSpacing.default() }
