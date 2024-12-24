package com.example.ebooks.presentation.navigation


import kotlinx.serialization.Serializable

@Serializable
sealed class routes {

    @Serializable
    data class BookByCategory(
        val category: String
        )

    @Serializable
    data class pdfViewer(
        val pdfUrl: String
    )

    @Serializable
    object HomeScreen


}
