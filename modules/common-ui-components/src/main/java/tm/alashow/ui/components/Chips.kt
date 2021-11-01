/*
 * Copyright (C) 2021, Alashov Berkeli
 * All rights reserved.
 */
package tm.alashow.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tm.alashow.ui.theme.AppTheme
import tm.alashow.ui.theme.DefaultThemeDark

@Composable
fun <T : Any> ChipsRow(
    items: List<T>,
    selectedItems: Set<T>,
    onItemSelect: (Boolean, T) -> Unit,
    modifier: Modifier = Modifier,
    labelMapper: @Composable (T) -> String = { it.toString().replaceFirstChar { c -> c.uppercase() } },
) {
    val scrollState = rememberLazyListState()
    val firstSelectedIndex = if (selectedItems.isEmpty()) null else items.indexOf(selectedItems.first())
    LaunchedEffect(firstSelectedIndex) {
        if (firstSelectedIndex != null)
            scrollState.animateScrollToItem(firstSelectedIndex)
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.specs.paddingSmall),
        state = scrollState,
        contentPadding = PaddingValues(horizontal = AppTheme.specs.padding, vertical = AppTheme.specs.paddingTiny),
        modifier = modifier,
    ) {
        items(items, null) { item ->
            val isSelected = selectedItems.contains(item)
            Chip(
                selected = isSelected,
                label = labelMapper(item),
                modifier = Modifier.toggleable(
                    value = isSelected,
                    onValueChange = { onItemSelect(it, item) }
                ),
            )
        }
    }
}

@Composable
fun Chip(
    selected: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    selectedBackground: Color = MaterialTheme.colorScheme.secondary,
    selectedContentColor: Color = contentColorFor(selectedBackground),
    unselectedBackground: Color = if (AppTheme.colors.isLight) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Surface(
        color = when {
            selected -> selectedBackground
            else -> unselectedBackground
        },
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> selectedBackground
                else -> unselectedContentColor
            }
        ),
        contentColor = when {
            selected -> selectedContentColor
            else -> unselectedContentColor
        },
        shape = CircleShape,
        elevation = 0.dp
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(AppTheme.specs.inputPaddings)
        )
    }
}

@Preview
@Composable
fun ChipsPreview() {
    val items = listOf("Songs", "Artists", "Albums")
    AppTheme(DefaultThemeDark) {
        ChipsRow(items, setOf(items.first()), { _, _ -> })
    }
}
