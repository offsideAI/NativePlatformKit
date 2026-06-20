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
package das.arunabh.nativeplatformkit.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import das.arunabh.nativeplatformkit.theme.NpkTheme

/**
 * The visual emphasis of an [NpkButton].
 */
public enum class NpkButtonStyle {
    /** Highest emphasis: a solid [NpkTheme.colors] primary container. */
    Filled,

    /** Medium emphasis: a tonal primary container. */
    Tonal,

    /** Low emphasis: a transparent container with an outline. */
    Outlined,
}

/**
 * A themed button — **the canonical reference component for NativePlatformKit**.
 *
 * `NpkButton` is intentionally trivial; it exists to demonstrate the complete end-to-end pattern
 * that every NPK component follows (idiomatic Compose API, KDoc, previews, unit/UI test, screenshot
 * test, catalog entry, and a committed public-API entry). It is **not** a production component of
 * the library catalog — see `docs/adding-a-component.md`, which is derived from this file.
 *
 * The API follows Compose conventions: a leading action lambda, a `modifier` parameter that
 * defaults to [Modifier] and is the first optional parameter, sensible defaults for everything
 * else, and a trailing `content` slot scoped to [RowScope]. All visual values are read from
 * [NpkTheme]; nothing is hard-coded.
 *
 * Example:
 * ```
 * NpkButton(onClick = viewModel::save) {
 *     Text("Save")
 * }
 * ```
 *
 * @param onClick called when the button is clicked.
 * @param modifier the [Modifier] to apply to this button.
 * @param enabled controls the enabled state. When `false` the button is visually muted and does
 *   not respond to input.
 * @param style the visual emphasis of the button. See [NpkButtonStyle].
 * @param content the button label, laid out in a centered [RowScope]. Typically a `Text`,
 *   optionally with a leading icon.
 */
@Composable
public fun NpkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: NpkButtonStyle = NpkButtonStyle.Filled,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = NpkTheme.colors
    val shapes = NpkTheme.shapes
    val spacing = NpkTheme.spacing
    val elevation = NpkTheme.elevation

    val containerColor: Color =
        when (style) {
            NpkButtonStyle.Filled -> colors.primary
            NpkButtonStyle.Tonal -> colors.primaryContainer
            NpkButtonStyle.Outlined -> Color.Transparent
        }
    val contentColor: Color =
        when (style) {
            NpkButtonStyle.Filled -> colors.onPrimary
            NpkButtonStyle.Tonal -> colors.onPrimaryContainer
            NpkButtonStyle.Outlined -> colors.primary
        }
    val border: BorderStroke? =
        if (style == NpkButtonStyle.Outlined) {
            BorderStroke(width = 1.dp, color = if (enabled) colors.outline else colors.outline.copy(alpha = DISABLED_ALPHA))
        } else {
            null
        }
    val shadow = if (style == NpkButtonStyle.Filled) elevation.level1 else elevation.level0

    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shapes.medium,
        color = if (enabled) containerColor else containerColor.copy(alpha = DISABLED_CONTAINER_ALPHA),
        contentColor = if (enabled) contentColor else contentColor.copy(alpha = DISABLED_ALPHA),
        border = border,
        shadowElevation = if (enabled) shadow else elevation.level0,
    ) {
        ProvideTextStyle(value = NpkTheme.typography.label) {
            Row(
                modifier =
                    Modifier
                        .defaultMinSize(minWidth = MIN_WIDTH, minHeight = MIN_TOUCH_TARGET)
                        .padding(horizontal = spacing.medium, vertical = spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.small, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}

/** Alpha applied to content/border of a disabled button. */
private const val DISABLED_ALPHA = 0.38f

/** Alpha applied to the container of a disabled button. */
private const val DISABLED_CONTAINER_ALPHA = 0.12f

/** Minimum touch-target height for accessibility (matches Material's 48dp guidance). */
private val MIN_TOUCH_TARGET = 48.dp

/** A small minimum width so short labels still produce a comfortably tappable button. */
private val MIN_WIDTH = 64.dp

// ---------------------------------------------------------------------------
// Previews — private, so they never leak into the public API. These are the
// gold-standard previews every component should ship (light, dark, states).
// ---------------------------------------------------------------------------

@Preview(name = "Filled · Light", showBackground = true)
@Composable
private fun NpkButtonFilledLightPreview() {
    NpkTheme(darkTheme = false) {
        NpkButton(onClick = {}) { Text("Filled button") }
    }
}

@Preview(name = "Filled · Dark", showBackground = true)
@Composable
private fun NpkButtonFilledDarkPreview() {
    NpkTheme(darkTheme = true) {
        NpkButton(onClick = {}) { Text("Filled button") }
    }
}

@Preview(name = "Styles", showBackground = true)
@Composable
private fun NpkButtonStylesPreview() {
    NpkTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NpkButton(onClick = {}, style = NpkButtonStyle.Filled) { Text("Filled") }
            NpkButton(onClick = {}, style = NpkButtonStyle.Tonal) { Text("Tonal") }
            NpkButton(onClick = {}, style = NpkButtonStyle.Outlined) { Text("Outlined") }
        }
    }
}

@Preview(name = "Disabled", showBackground = true)
@Composable
private fun NpkButtonDisabledPreview() {
    NpkTheme {
        NpkButton(onClick = {}, enabled = false) { Text("Disabled") }
    }
}
