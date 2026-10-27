package com.example.comuthtodo.ui.illustration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comuthtodo.model.Category

val Primary = Color(0xFF3B5BE0)
val SoftBg = Color(0xFFECEEFB)

/** Lớp cha trừu tượng: mỗi trang onboarding có một hình minh họa riêng. */
abstract class Illustration {
    @Composable
    abstract fun Render()

    @Composable
    protected fun Frame(content: @Composable ColumnScope.() -> Unit) {
        Column(
            modifier = Modifier
                .size(width = 200.dp, height = 240.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(SoftBg)
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

/** Trang 1: danh sách việc cần làm. */
class TaskListIllustration : Illustration() {
    @Composable
    override fun Render() = Frame {
        repeat(3) { index ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(18.dp).clip(CircleShape)
                            .then(
                                if (index == 0) Modifier.background(Primary)
                                else Modifier.border(1.dp, Color.Gray, CircleShape)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index == 0) Icon(
                            Icons.Default.Check, null,
                            tint = Color.White, modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Box(Modifier.height(5.dp).weight(1f).clip(CircleShape).background(Color(0xFFD5D8E8)))
                }
            }
        }
    }
}

/** Trang 2: chia theo Học tập / Cuộc sống (dùng enum Category). */
class CategoryIllustration : Illustration() {
    @Composable
    override fun Render() = Frame {
        TagCard(Category.HOC_TAP)
        Spacer(Modifier.height(10.dp))
        TagCard(Category.CUOC_SONG)
    }

    @Composable
    private fun TagCard(category: Category) {
        Surface(shape = RoundedCornerShape(12.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                Text(
                    category.label, color = category.textColor, fontSize = 11.sp,
                    modifier = Modifier.clip(RoundedCornerShape(6.dp))
                        .background(category.bgColor)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
                Spacer(Modifier.height(8.dp))
                Box(Modifier.height(5.dp).fillMaxWidth(0.8f).clip(CircleShape).background(Color(0xFFD5D8E8)))
            }
        }
    }
}

/** Trang 3: nhắc nhở. */
class ReminderIllustration : Illustration() {
    @Composable
    override fun Render() = Frame {
        Box(
            Modifier.size(56.dp).clip(CircleShape).background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Notifications, null, tint = Primary)
        }
        Spacer(Modifier.height(16.dp))
        Surface(shape = RoundedCornerShape(10.dp), color = Color.White) {
            Text(
                "Ôn bài Toán lúc 19:00", fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}