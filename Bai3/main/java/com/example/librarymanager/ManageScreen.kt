package com.example.librarymanager

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ManageScreen(vm: LibraryViewModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(vm.currentStudent?.name ?: "") }
    val selected = remember { mutableStateListOf<Int>() }

    // Khi đổi sinh viên -> tick sẵn các sách họ đã mượn
    LaunchedEffect(vm.currentId) {
        selected.clear()
        selected.addAll(vm.currentStudent?.borrowed ?: emptyList())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "Hệ thống\nQuản lý Thư viện",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(40.dp))

        Text("Sinh viên", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    val s = vm.findStudentByName(name)
                    if (s != null) vm.currentId = s.id
                    else Toast.makeText(
                        context,
                        "Không tìm thấy sinh viên. Hãy thêm ở tab Sinh viên",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = RoundedCornerShape(8.dp)
            ) { Text("Thay đổi") }
        }

        Spacer(Modifier.height(20.dp))
        Text("Danh sách sách", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFDDDDDD))
                .padding(10.dp)
        ) {
            LazyColumn {
                items(vm.books, key = { it.id }) { book ->
                    val checked = selected.contains(book.id)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable {
                                if (checked) selected.remove(book.id) else selected.add(book.id)
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                if (it) selected.add(book.id) else selected.remove(book.id)
                            }
                        )
                        Text(book.title)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                vm.currentStudent?.let {
                    vm.setBorrowed(it.id, selected.toList())
                    Toast.makeText(
                        context,
                        "${it.name} đã mượn ${selected.size} cuốn sách",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp)
        ) { Text("Thêm") }
    }
}