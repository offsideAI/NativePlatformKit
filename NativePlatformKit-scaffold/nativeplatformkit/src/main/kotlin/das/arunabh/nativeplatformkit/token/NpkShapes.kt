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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

/**
 * The corner-shape scale consumed by NativePlatformKit components. Components read shapes from
 * `NpkTheme.shapes` rather than constructing their own.
 *
 * Instances are [Immutable].
 */
@Immutable
public class NpkShapes(
    /** Small components: chips, text fields, small buttons. */
    public val small: RoundedCornerShape,
    /** Medium components: standard buttons, cards. */
    public val medium: RoundedCornerShape,
    /** Large components: sheets, dialogs, large cards. */
    public val large: RoundedCornerShape,
) {
    /** Returns a copy overriding only the supplied shapes. */
    public fun copy(
        small: RoundedCornerShape = this.small,
        medium: RoundedCornerShape = this.medium,
        large: RoundedCornerShape = this.large,
    ): NpkShapes = NpkShapes(small, medium, large)

    /** Projects these shapes onto a Material 3 [Shapes] instance. */
    public fun toMaterialShapes(): Shapes =
        Shapes(
            small = small,
            medium = medium,
            large = large,
        )

    public companion object {
        /** The default NativePlatformKit shape scale. */
        public fun default(): NpkShapes =
            NpkShapes(
                small = RoundedCornerShape(8.dp),
                medium = RoundedCornerShape(12.dp),
                large = RoundedCornerShape(20.dp),
            )
    }
}

/**
 * [androidx.compose.runtime.CompositionLocal] carrying the current [NpkShapes]. Prefer reading via
 * `NpkTheme.shapes`.
 */
public val LocalNpkShapes: androidx.compose.runtime.ProvidableCompositionLocal<NpkShapes> =
    staticCompositionLocalOf { NpkShapes.default() }
