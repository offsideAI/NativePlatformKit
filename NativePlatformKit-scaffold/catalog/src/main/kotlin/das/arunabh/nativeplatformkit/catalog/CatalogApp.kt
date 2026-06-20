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
package das.arunabh.nativeplatformkit.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import das.arunabh.nativeplatformkit.component.NpkButton
import das.arunabh.nativeplatformkit.component.NpkButtonStyle
import das.arunabh.nativeplatformkit.theme.NpkTheme

/**
 * Root of the catalog. Hosts a dark-mode toggle and the list of component demos. As components are
 * added in phase two, register a new [ComponentDemo] section here.
 */
@Composable
fun CatalogApp() {
    var darkTheme by remember { mutableStateOf(false) }

    NpkTheme(darkTheme = darkTheme) {
        Surface(modifier = Modifier.fillMaxSize(), color = NpkTheme.colors.background) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(NpkTheme.spacing.large),
                verticalArrangement = Arrangement.spacedBy(NpkTheme.spacing.large),
            ) {
                Header(darkTheme = darkTheme, onDarkThemeChange = { darkTheme = it })
                HorizontalDivider(color = NpkTheme.colors.outline)
                NpkButtonDemo()
            }
        }
    }
}

@Composable
private fun Header(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(NpkTheme.spacing.small)) {
        Text(text = "NativePlatformKit Catalog", style = NpkTheme.typography.display, color = NpkTheme.colors.onBackground)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NpkTheme.spacing.small),
        ) {
            Text(text = "Dark theme", style = NpkTheme.typography.body, color = NpkTheme.colors.onBackground)
            Switch(checked = darkTheme, onCheckedChange = onDarkThemeChange)
        }
    }
}

/** Demo section for the reference [NpkButton] component. */
@Composable
private fun NpkButtonDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(NpkTheme.spacing.medium)) {
        Text(text = "NpkButton", style = NpkTheme.typography.title, color = NpkTheme.colors.onBackground)
        Row(horizontalArrangement = Arrangement.spacedBy(NpkTheme.spacing.small), verticalAlignment = Alignment.CenterVertically) {
            NpkButton(onClick = {}, style = NpkButtonStyle.Filled) { Text("Filled") }
            NpkButton(onClick = {}, style = NpkButtonStyle.Tonal) { Text("Tonal") }
            NpkButton(onClick = {}, style = NpkButtonStyle.Outlined) { Text("Outlined") }
        }
        NpkButton(onClick = {}, enabled = false) { Text("Disabled") }
    }
}

@Preview(showBackground = true)
@Composable
private fun CatalogAppPreview() {
    CatalogApp()
}
