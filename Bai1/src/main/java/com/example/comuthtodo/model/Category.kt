package com.example.comuthtodo.model

import androidx.compose.ui.graphics.Color

/** Nhóm công việc: mỗi nhóm có tên và màu riêng. */
enum class Category(
    val label: String,
    val bgColor: Color,
    val textColor: Color
) {
    HOC_TAP("Học tập", Color(0xFFDDE3FB), Color(0xFF3B5BE0)),
    CUOC_SONG("Cuộc sống", Color(0xFFFDE8D6), Color(0xFFC0651A))
}