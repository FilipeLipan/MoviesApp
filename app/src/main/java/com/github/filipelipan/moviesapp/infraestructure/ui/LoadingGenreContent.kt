package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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
            if(loading) {
                loadingContent()
            } else {
                content()
            }
        }
    }
}
