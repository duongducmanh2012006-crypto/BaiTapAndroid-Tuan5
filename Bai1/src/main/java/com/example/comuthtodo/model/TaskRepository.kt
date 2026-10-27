package com.example.comuthtodo.data

import com.example.comuthtodo.model.Category
import com.example.comuthtodo.model.Task

/** Danh sách việc mẫu. */
object TaskRepository {
    val tasks: List<Task> = listOf(
        Task(1, "Ôn bài Toán", Category.HOC_TAP, "19:00"),
        Task(2, "Làm bài tập Android", Category.HOC_TAP, "21:00"),
        Task(3, "Rửa chén", Category.CUOC_SONG, "20:00"),
        Task(4, "Đi chợ", Category.CUOC_SONG, "17:30", done = true)
    )
}