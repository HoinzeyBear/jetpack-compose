package com.example.jetpackcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun ThemeSettingItem(
    modifier: Modifier = Modifier,
    selectedTheme: Theme,
    onOptionSelected: (option: Theme) -> Unit){
    var expanded by remember { mutableStateOf(false) }
    SettingItem {
        Row(modifier = Modifier
            .clickable(onClick = { expanded = !expanded },
            onClickLabel = stringResource(
            id = R.string.select_theme))
            .padding(16.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id =
                R.string.setting_option_theme))
            Text(text = stringResource(id = selectedTheme.label))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(16.dp, 0.dp)) {
            Theme.values().forEach { theme ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(theme)
                    expanded = false
                }) {
                    Text(text = stringResource(id = theme.label))
                }
            }
        }
    }
}