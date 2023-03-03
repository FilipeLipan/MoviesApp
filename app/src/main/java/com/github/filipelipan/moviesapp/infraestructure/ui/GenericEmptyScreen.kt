package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.github.filipelipan.moviesapp.ui.theme.Spacing
import com.github.filipelipan.moviesapp.R

@Composable
fun GenericEmptyScreen(
    title: String,
    buttonName: String,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieLoader(
                modifier = Modifier.size(200.dp),
                rawResource = R.raw.empty,
                contentScale = ContentScale.Fit
            )

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
