package com.woosuk.designsystem.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButtonTopAppBar(
    modifier: Modifier = Modifier,
    onClickBackButton: () -> Unit,
    title: @Composable () -> Unit,
    actions: @Composable (RowScope.() -> Unit) = {},
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            Icon(
                modifier =
                    Modifier
                        .padding(start = 16.dp, bottom = 16.dp, top = 16.dp, end = 16.dp)
                        .clickable {
                            onClickBackButton()
                        }
                        .padding(4.dp),
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "BackArrowLeft",
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = WoosukTheme.colors.Black0),
        windowInsets = windowInsets,
    )
}
