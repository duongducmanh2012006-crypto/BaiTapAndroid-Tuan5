package com.example.librarymanager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BooksScreen(vm: LibraryViewModel) {
    var title by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Danh sách sách", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tên sách") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (title.isNotBlank()) {
                    vm.addBook(title.trim())
                    title = ""
                }
            }) { Text("Thêm") }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(vm.books, key = { it.id }) { book ->
                Text("📖 ${book.title}", modifier = Modifier.padding(vertical = 10.dp))
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun StudentsScreen(vm: LibraryViewModel) {
    var name by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Danh sách sinh viên", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tên sinh viên") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (name.isNotBlank()) {
                    vm.addStudent(name.trim())
                    name = ""
                }
            }) { Text("Thêm") }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(vm.students, key = { it.id }) { st ->
                val titles = st.borrowed.mapNotNull { id ->
                    vm.books.firstOrNull { it.id == id }?.title
                }
                Column(Modifier.padding(vertical = 10.dp)) {
                    Text(st.name, fontWeight = FontWeight.Bold)
                    Text(
                        "Đã mượn: " +
                                if (titles.isEmpty()) "chưa mượn sách nào" else titles.joinToString(", ")
                    )
                }
                HorizontalDivider()
            }
        }
    }
}