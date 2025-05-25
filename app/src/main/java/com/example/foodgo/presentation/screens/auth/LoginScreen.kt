package com.example.foodgo.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.foodgo.R
import com.example.foodgo.presentation.viewmodel.auth.LoginViewModel
import com.example.foodgo.ui.theme.* // Импортируем цвета из темы
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel(), onLoginSuccess: () -> Unit, onNavigateToSignUp: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    // Обработка состояния из ViewModel
    val loginState by loginViewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginViewModel.LoginResult.Success -> {
                Toast.makeText(
                    context,
                    "Успешный вход",
                    Toast.LENGTH_SHORT
                ).show()
                onLoginSuccess() // Переход на следующий экран
                loginViewModel.resetLoginState() // Дополнительная страховка
            }
            is LoginViewModel.LoginResult.Error -> {
                Toast.makeText(
                    context,
                    (loginState as LoginViewModel.LoginResult.Error).message,
                    Toast.LENGTH_LONG
                ).show()
                loginViewModel.resetLoginState()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(DarkBlack)) {
        // Добавление изображения на фон
        Image(
            painter = painterResource(id = R.drawable.bg_asset_login),
            contentDescription = "Background Image",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-50).dp)
                .width(449.dp)
                .height(449.dp)
        )

        // Заголовок
        Text(
            text = "Войти",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 118.dp)
        )

        Text(
            text = "Войдите в существующий аккаунт",
            fontSize = 16.sp,
            color = White,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.TopCenter)
                .padding(top = 157.dp)
                .alpha(0.85f)
        )

        // Карточка с логином
        Card(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 233.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            colors = CardDefaults.cardColors(containerColor = White) // Белый фон внутри карточки
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ПОЧТА",
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Start),
                )

                // Поле ввода Email
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("example@gmail.com", fontSize = 14.sp, color = PlaceholderGrey) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = GreyLight
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Text(
                    text = "ПАРОЛЬ",
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 24.dp).align(Alignment.Start),
                )

                // Поле ввода пароля
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(modifier = Modifier.size(19.dp, 14.dp), onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                tint = PlaceholderGrey,
                                modifier = Modifier.size(19.dp, 14.dp),
                                painter = if (passwordVisible) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.eye2),
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = GreyLight
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Ссылка "Forgot Password?"
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        modifier = Modifier.size(20.dp),
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = Orange, uncheckedColor = Color(0xFFE3EBF2))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Запомнить меня",
                        fontSize = 14.sp,
                        color = PlaceholderGrey,
                        modifier = Modifier.clickable { rememberMe = !rememberMe }
                    )


                }

                Spacer(modifier = Modifier.height(31.dp))

                // Кнопка "Log In"
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                        } else {
                            loginViewModel.onLoginClick(email, password, rememberMe)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "ВОЙТИ", color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(38.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ещё нет аккаунта?",
                        fontSize = 16.sp,
                        color = PlaceholderGrey
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "СОЗДАТЬ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Orange,
                        modifier = Modifier.clickable { onNavigateToSignUp() }
                    )
                }
            }
        }
    }

    when (val state = loginState) {
        is LoginViewModel.LoginResult.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
        is LoginViewModel.LoginResult.Success -> {
            Toast.makeText(context, "Успешный вход!", Toast.LENGTH_SHORT).show()
            loginViewModel.resetLoginState()
            onLoginSuccess()
        }
        else -> Unit
    }
}

