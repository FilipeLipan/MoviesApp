package com.github.filipelipan.moviesapp.infraestructure.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun LoadingContent(
    loading: Boolean,
    error: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    errorContent: @Composable () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (empty) {
        Box(
            modifier = modifier
        ){
            emptyContent()
        }
    } else {
        if(error){
            Box(
                modifier = modifier
            ){
                errorContent()
            }
        } else {
            SwipeRefresh(
                state = rememberSwipeRefreshState(loading),
                onRefresh = onRefresh,
                modifier = modifier,
                content = content,
            )
        }
    }
}
