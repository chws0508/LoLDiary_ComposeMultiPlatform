package com.woosuk.designsystem.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme

@Composable
fun WoosukCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color = WoosukTheme.colors.Black40),
        colors =
            CardDefaults.cardColors(
                containerColor = WoosukTheme.colors.Black0,
            ),
    ) {
        content()
    }
}
