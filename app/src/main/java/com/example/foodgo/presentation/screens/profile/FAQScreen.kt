package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.ui.theme.GreyLight
import com.example.foodgo.ui.theme.IconGrey3

data class FAQItem(val question: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp, bottom = 29.dp, start = 24.dp, end = 24.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(GreyLight, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = IconGrey3,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Часто задаваемые вопросы",
                color = Color.Black,
                fontSize = 17.sp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        val faqList = listOf(
            FAQItem(
                question = "Как добавить блюдо в избранное?",
                answer = "Нажмите на иконку сердечка рядом с блюдом, чтобы добавить его в избранное."
            ),
            FAQItem(
                question = "Можно ли изменить заказ после оформления?",
                answer = "Изменить заказ можно только до его подтверждения."
            ),
            FAQItem(
                question = "Какие способы оплаты доступны?",
                answer = "Мы принимаем оплату картами, через электронные кошельки и наличными курьеру."
            ),
            FAQItem(
                question = "Как связаться с поддержкой?",
                answer = "Вы можете написать в чат поддержки или позвонить по номеру 8-800-555-35-35."
            )
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F7))
        ) {
            items(faqList) { item ->
                FAQItemView(item)
            }
        }
    }
}

@Composable
fun FAQItemView(item: FAQItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Question icon",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            text = item.answer,
            fontSize = 15.sp,
            color = Color(0xFF666666),
            lineHeight = 22.sp
        )
    }
}
