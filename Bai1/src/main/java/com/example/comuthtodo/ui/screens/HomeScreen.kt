package com.example.comuthtodo.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comuthtodo.model.AppInfo
import com.example.comuthtodo.model.Category
import com.example.comuthtodo.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(appInfo: AppInfo, initialTasks: List<Task>) {
    val tasks = remember { mutableStateListOf<Task>().apply { addAll(initialTasks) } }
    var filter by remember { mutableStateOf<Category?>(null) }
    val shown = if (filter == null) tasks.toList() else tasks.filter { it.category == filter }

    Column(Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Text(appInfo.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            "Hôm nay bạn có ${tasks.count { !it.done }} việc cần làm",
            color = Color.Gray, fontSize = 14.sp
        )
        Spacer(Modifier.height(16.dp))

        // Bộ lọc theo nhóm
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = filter == null,
                onClick = { filter = null },
                label = { Text("Tất cả") }
            )
            Category.entries.forEach { c ->
                FilterChip(
                    selected = filter == c,
                    onClick = { filter = c },
                    label = { Text(c.label) }
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        // Danh sách việc
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(shown, key = { it.id }) { task ->
                TaskCard(task) {
                    val i = tasks.indexOfFirst { it.id == task.id }
                    tasks[i] = task.copy(done = !task.done)
                }
            }
        }
    }
}

@Composable
private fun TaskCard(task: Task, onToggle: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF5F6FC),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = task.done, onCheckedChange = { onToggle() })
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    task.title,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    task.category.label,
                    color = task.category.textColor,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(task.category.bgColor)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            Text(task.dueTime, color = Color.Gray, fontSize = 13.sp)
        }
    }
}