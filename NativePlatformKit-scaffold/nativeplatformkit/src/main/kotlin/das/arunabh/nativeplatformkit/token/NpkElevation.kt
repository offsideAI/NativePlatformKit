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
 * The elevation scale consumed by NativePlatformKit components for shadows and tonal overlays.
 * Components read elevation from `NpkTheme.elevation` rather than hard-coding [Dp]s.
 *
 * Instances are [Immutable].
 */
@Immutable
public class NpkElevation(
    /** Flush with the surface (0dp). */
    public val level0: Dp,
    /** Resting elevation for raised components (1dp). */
    public val level1: Dp,
    /** Low elevation (3dp). */
    public val level2: Dp,
    /** Medium elevation (6dp). */
    public val level3: Dp,
    /** High elevation, e.g. for pressed/dragged components (8dp). */
    public val level4: Dp,
) {
    /** Returns a copy overriding only the supplied values. */
    public fun copy(
        level0: Dp = this.level0,
        level1: Dp = this.level1,
        level2: Dp = this.level2,
        level3: Dp = this.level3,
        level4: Dp = this.level4,
    ): NpkElevation = NpkElevation(level0, level1, level2, level3, level4)

    public companion object {
        /** The default NativePlatformKit elevation scale. */
        public fun default(): NpkElevation =
            NpkElevation(
                level0 = 0.dp,
                level1 = 1.dp,
                level2 = 3.dp,
                level3 = 6.dp,
                level4 = 8.dp,
            )
    }
}

/**
 * [androidx.compose.runtime.CompositionLocal] carrying the current [NpkElevation]. Prefer reading
 * via `NpkTheme.elevation`.
 */
public val LocalNpkElevation: androidx.compose.runtime.ProvidableCompositionLocal<NpkElevation> =
    staticCompositionLocalOf { NpkElevation.default() }
