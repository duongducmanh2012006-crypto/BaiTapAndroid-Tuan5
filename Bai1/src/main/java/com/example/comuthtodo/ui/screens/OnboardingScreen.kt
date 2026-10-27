package com.example.comuthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comuthtodo.model.AppInfo
import com.example.comuthtodo.model.OnboardingPage
import com.example.comuthtodo.ui.illustration.Primary
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    appInfo: AppInfo,
    pages: List<OnboardingPage>,
    onFinished: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == pages.lastIndex

    Column(Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {

        // Header: logo + Bỏ qua
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(Primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Checklist, null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = buildAnnotatedString {
                    append(appInfo.name.substringBefore(" ") + " ")
                    withStyle(SpanStyle(color = Primary)) {
                        append(appInfo.name.substringAfter(" "))
                    }
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                "Bỏ qua", color = Color.Gray, fontSize = 13.sp,
                modifier = Modifier.clickable { onFinished() }
            )
        }

        // Nội dung từng trang
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    page.illustration.Render()   // đa hình: mỗi trang tự vẽ hình của mình
                }
                Text(page.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(page.description, color = Color.Gray, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))
            }
        }

        // Chỉ báo trang
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            pages.forEachIndexed { i, _ ->
                val selected = i == pagerState.currentPage
                Box(
                    Modifier.height(6.dp).width(if (selected) 24.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (selected) Primary else Color(0xFFD5D8E8))
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        // Nút Tiếp tục / Bắt đầu ngay
        Button(
            onClick = {
                if (isLast) onFinished()
                else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text(pages[pagerState.currentPage].buttonText)
        }
    }
}