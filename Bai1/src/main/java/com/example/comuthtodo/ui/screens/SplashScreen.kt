package com.example.comuthtodo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comuthtodo.model.AppInfo
import com.example.comuthtodo.ui.illustration.Primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(appInfo: AppInfo, onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }
    Box(Modifier.fillMaxSize().background(Primary)) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.size(96.dp).clip(RoundedCornerShape(24.dp)).background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Checklist, null, tint = Primary, modifier = Modifier.size(52.dp))
            }
            Spacer(Modifier.height(16.dp))
            Text(appInfo.name, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(appInfo.tagline, color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
        }
        Column(
            Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier.width(110.dp),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
            Spacer(Modifier.height(8.dp))
            Text(appInfo.versionLabel, color = Color.White.copy(alpha = 0.85f), fontSize = 11.sp)
        }
    }
}
