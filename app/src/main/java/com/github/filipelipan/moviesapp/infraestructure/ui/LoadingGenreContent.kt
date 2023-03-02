package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingGenreContent(
    loading: Boolean,
    empty: Boolean,
    modifier: Modifier = Modifier,
    emptyContent: @Composable () -> Unit,
    loadingContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        if (empty) {
            emptyContent()
        } else {
            if (loading) {
                loadingContent()
            } else {
                content()
            }
        }
    }
}
