package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.github.filipelipan.moviesapp.ui.theme.Spacing
import androidx.compose.ui.Alignment


@Composable
fun GenericErrorScreen(
    title: String,
    buttonName: String,
    buttonAction: () -> Unit,
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = title,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,

                )

            Spacer(modifier = Modifier.height(Spacing.level8))

            Button(
                modifier = Modifier.wrapContentSize(),
                onClick = { buttonAction.invoke() }
            ) {
                Text(
                    text = buttonName,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}