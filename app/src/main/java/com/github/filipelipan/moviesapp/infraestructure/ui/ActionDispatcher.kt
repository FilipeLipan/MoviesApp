package com.github.filipelipan.moviesapp.infraestructure.ui

interface PresentationModel<T, A> {
    val uiState: T
    fun dispatchViewAction(viewAction: A)
}

interface ActionDispatcher<T, A> : PresentationModel<T, A>
