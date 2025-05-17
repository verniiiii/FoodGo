package com.example.foodgo.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodgo.R
import com.example.foodgo.presentation.viewmodel.auth.SignUpViewModel
import com.example.foodgo.ui.theme.Black
import com.example.foodgo.ui.theme.DarkBlack
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey6
import com.example.foodgo.ui.theme.PlaceholderGrey
import com.example.foodgo.ui.theme.Orange
import com.example.foodgo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var reTypePassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var reTypePasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Обработка результатов регистрации
    val registrationResult by viewModel.registrationResult.collectAsState()

    LaunchedEffect(registrationResult) {
        registrationResult?.fold(
            onSuccess = { authResponse ->
                Toast.makeText(
                    context,
                    "Успешная регистрация: ${authResponse.token}",
                    Toast.LENGTH_LONG
                ).show()
                onBackClick()
            },
            onFailure = { error ->
                Toast.makeText(
                    context,
                    "Ошибка регистрации: ${error.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetRegistrationResult()
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(DarkBlack)) {
        // Фоновое изображение
        Image(
            painter = painterResource(id = R.drawable.bg_asset_login),
            contentDescription = "Background Image",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-50).dp)
                .width(449.dp)
                .height(449.dp)
        )

        // Кнопка возврата
        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier
                .padding(start = 24.dp, top = 50.dp)
                .align(Alignment.TopStart)
                .size(45.dp)
                .background(White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = IconGrey6,
                modifier = Modifier.size(18.dp)
            )
        }

        // Заголовки
        Text(
            text = "Зарегистрироваться",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 119.dp)
        )
        Text(
            text = "Зарегистрируйтесь, чтобы начать",
            fontSize = 16.sp,
            color = White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 157.dp)
                .alpha(0.85f)
        )

        // Основная карточка с формой
        Card(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 233.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Поле ввода имени
                Text(
                    text = "ИМЯ",
                    fontSize = 13.sp,
                    color = Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Шестопалова Вероника", fontSize = 14.sp, color = PlaceholderGrey) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(62.dp),
                    singleLine = true,
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = GreyLight
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Поле ввода email
                Text(
                    text = "ПОЧТА",
                    fontSize = 13.sp,
                    color = Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("example@gmail.com", fontSize = 14.sp, color = PlaceholderGrey) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(62.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = GreyLight
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Поле ввода пароля
                Text(
                    text = "ПАРОЛЬ",
                    fontSize = 13.sp,
                    color = Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("* * * * * * * *", fontSize = 14.sp, color = PlaceholderGrey) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(62.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.size(19.dp, 14.dp),
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                tint = PlaceholderGrey,
                                modifier = Modifier.size(19.dp, 14.dp),
                                painter = if (passwordVisible) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.eye2),
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = GreyLight
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Поле повторного ввода пароля
                Text(
                    text = "ПОВТОРИТЕ ПАРОЛЬ",
                    fontSize = 13.sp,
                    color = Black,
                    modifier = Modifier.align(Alignment.Start)
                )
                TextField(
                    value = reTypePassword,
                    onValueChange = { reTypePassword = it },
                    placeholder = { Text("* * * * * * * *", fontSize = 14.sp, color = PlaceholderGrey) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(62.dp),
                    visualTransformation = if (reTypePasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.size(19.dp, 14.dp),
                            onClick = { reTypePasswordVisible = !reTypePasswordVisible }
                        ) {
                            Icon(
                                tint = PlaceholderGrey,
                                modifier = Modifier.size(19.dp, 14.dp),
                                painter = if (reTypePasswordVisible) painterResource(id = R.drawable.eye) else painterResource(id = R.drawable.eye2),
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = GreyLight
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Кнопка регистрации
                Button(
                    onClick = {
                        if (password != reTypePassword) {
                            Toast.makeText(
                                context,
                                "Пароли не совпадают",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            viewModel.register(name, email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        disabledContainerColor = Orange.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "ЗАРЕГИСТРИРОВАТЬСЯ",
                        color = White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


