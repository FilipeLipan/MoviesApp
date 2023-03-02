package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.github.filipelipan.moviesapp.ui.theme.Spacing


@Composable
fun GenericErrorScreen(
    title: String,
    buttonName: String,
    onButtonClick: () -> Unit,
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
                onClick = { onButtonClick.invoke() }
            ) {
                Text(
                    text = buttonName,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}
