package com.example.comuthtodo.model

/** Một việc cần làm. */
data class Task(
    val id: Int,
    val title: String,
    val category: Category,
    val dueTime: String,
    val done: Boolean = false
)