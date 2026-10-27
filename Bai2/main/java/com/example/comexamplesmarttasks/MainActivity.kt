package com.example.comexamplesmarttasks

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private val Blue = Color(0xFF4A90E8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { AppNav() } }
    }
}

// ================= NAVIGATION (Data Flow) =================
// Man 1 --email--> Man 2 --email, otp--> Man 3 --email, otp, pass--> Man 4
// Man 4 (Submit) --email, otp, pass--> Man 1 (hien trong khung xanh)
@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(
        navController = nav,
        startDestination = "forgot?email={email}&otp={otp}&pass={pass}"
    ) {
        // MAN 1
        composable(
            route = "forgot?email={email}&otp={otp}&pass={pass}",
            arguments = listOf("email", "otp", "pass").map {
                navArgument(it) { type = NavType.StringType; defaultValue = "" }
            }
        ) { e ->
            ForgotScreen(
                rEmail = e.arguments?.getString("email") ?: "",
                rOtp = e.arguments?.getString("otp") ?: "",
                rPass = e.arguments?.getString("pass") ?: "",
                onNext = { email -> nav.navigate("verify/${Uri.encode(email)}") }
            )
        }

        // MAN 2
        composable("verify/{email}") { e ->
            val email = e.arguments?.getString("email") ?: ""
            VerifyScreen(
                onBack = { nav.popBackStack() },
                onNext = { otp -> nav.navigate("reset/${Uri.encode(email)}/${Uri.encode(otp)}") }
            )
        }

        // MAN 3
        composable("reset/{email}/{otp}") { e ->
            val email = e.arguments?.getString("email") ?: ""
            val otp = e.arguments?.getString("otp") ?: ""
            ResetScreen(
                onBack = { nav.popBackStack() },
                onNext = { pass ->
                    nav.navigate("confirm/${Uri.encode(email)}/${Uri.encode(otp)}/${Uri.encode(pass)}")
                }
            )
        }

        // MAN 4
        composable("confirm/{email}/{otp}/{pass}") { e ->
            val email = e.arguments?.getString("email") ?: ""
            val otp = e.arguments?.getString("otp") ?: ""
            val pass = e.arguments?.getString("pass") ?: ""
            ConfirmScreen(
                email = email, otp = otp, pass = pass,
                onBack = { nav.popBackStack() },
                onSubmit = {
                    nav.navigate("forgot?email=${Uri.encode(email)}&otp=${Uri.encode(otp)}&pass=${Uri.encode(pass)}") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

// ================= 4 MAN HINH =================
@Composable
fun ForgotScreen(rEmail: String, rOtp: String, rPass: String, onNext: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    ScreenFrame("Forget Password?", "Enter your Email, we will send you a verification code.") {
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            placeholder = { Text("Your Email") }, singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        BlueButton("Next", enabled = email.isNotBlank()) { onNext(email.trim()) }

        // Khung xanh: du lieu nhan ve tu man hinh 4
        if (rEmail.isNotEmpty()) {
            Spacer(Modifier.height(32.dp))
            Column(
                Modifier.fillMaxWidth()
                    .background(Color(0xFFD9E7FA), RoundedCornerShape(24.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("email: $rEmail", color = Color.Red, fontSize = 20.sp)
                Text("otp: $rOtp", color = Color.Red, fontSize = 20.sp)
                Text("pass: $rPass", color = Color.Red, fontSize = 20.sp)
                Text("(từ màn hình 4)", color = Color.Red, fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun VerifyScreen(onBack: () -> Unit, onNext: (String) -> Unit) {
    var otp by remember { mutableStateOf("") }
    ScreenFrame(
        "Verify Code",
        "Enter the the code\nwe just sent you on your registered Email",
        onBack
    ) {
        BasicTextField(
            value = otp,
            onValueChange = { if (it.length <= 5 && it.all(Char::isDigit)) otp = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    repeat(5) { i ->
                        Box(
                            Modifier.size(52.dp)
                                .background(Color(0xFFF1F2F6), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) { Text(otp.getOrNull(i)?.toString() ?: "", fontSize = 22.sp) }
                    }
                }
            }
        )
        Spacer(Modifier.height(20.dp))
        BlueButton("Next", enabled = otp.length == 5) { onNext(otp) }
    }
}

@Composable
fun ResetScreen(onBack: () -> Unit, onNext: (String) -> Unit) {
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    ScreenFrame(
        "Create new password",
        "Your new password must be different form\npreviously used password",
        onBack
    ) {
        OutlinedTextField(
            value = pass, onValueChange = { pass = it },
            placeholder = { Text("Password") }, singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = confirm, onValueChange = { confirm = it },
            placeholder = { Text("Confirm Password") }, singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = confirm.isNotEmpty() && confirm != pass,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        BlueButton("Next", enabled = pass.isNotEmpty() && pass == confirm) { onNext(pass) }
    }
}

@Composable
fun ConfirmScreen(
    email: String, otp: String, pass: String,
    onBack: () -> Unit, onSubmit: () -> Unit
) {
    ScreenFrame("Confirm", "We are here to help you!", onBack) {
        OutlinedTextField(
            value = email, onValueChange = {}, readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = otp, onValueChange = {}, readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = pass, onValueChange = {}, readOnly = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        BlueButton("Submit", onClick = onSubmit)
    }
}

// ================= THANH PHAN DUNG CHUNG =================
@Composable
fun BlueButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = Blue),
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(26.dp)
    ) { Text(text, fontSize = 16.sp) }
}

@Composable
fun ScreenFrame(
    title: String,
    subtitle: String,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.fillMaxWidth().height(48.dp)) {
            if (onBack != null) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(48.dp).background(Blue, RoundedCornerShape(12.dp))
                ) { Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White) }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("UTH", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B8B))
        Text("SmartTasks", fontSize = 24.sp, color = Blue)
        Spacer(Modifier.height(32.dp))
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(subtitle, color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        content()
    }
}