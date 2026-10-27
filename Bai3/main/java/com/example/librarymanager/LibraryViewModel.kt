package com.example.librarymanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Book(val id: Int, val title: String)
data class Student(val id: Int, val name: String, val borrowed: List<Int> = emptyList())


    class LibraryViewModel : ViewModel() {
        val books = mutableStateListOf(
            Book(1, "Sách 01"),
            Book(2, "Sách 02"),
            Book(3, "Sách 03"),
            Book(4, "Sách 04"),
            Book(5, "Sách 05")
        )
        val students = mutableStateListOf(Student(1, "Nguyen Van A"))
        var currentId by mutableStateOf(1)

    private var nextBookId = 6
    private var nextStudentId = 2

    val currentStudent: Student?
        get() = students.firstOrNull { it.id == currentId }

    fun addBook(title: String) {
        books.add(Book(nextBookId++, title))
    }

    fun addStudent(name: String) {
        students.add(Student(nextStudentId++, name))
    }

    fun findStudentByName(name: String): Student? =
        students.firstOrNull { it.name.equals(name.trim(), ignoreCase = true) }

    fun setBorrowed(studentId: Int, bookIds: List<Int>) {
        val i = students.indexOfFirst { it.id == studentId }
        if (i >= 0) students[i] = students[i].copy(borrowed = bookIds)
    }
}