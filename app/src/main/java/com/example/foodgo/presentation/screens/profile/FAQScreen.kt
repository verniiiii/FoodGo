package com.example.foodgo.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.example.foodgo.data.remote.dto.FAQItem
import com.example.foodgo.presentation.components.ScreenHeader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(onBack: () -> Unit) {
    ScreenHeader(stringResource(R.string.FAQS), onBackClick = onBack) {
        val faqList = listOf(
            FAQItem(
                question = stringResource(R.string.question1),
                answer = stringResource(R.string.answer1)
            ),
            FAQItem(
                question = stringResource(R.string.question2),
                answer = stringResource(R.string.answer2)
            ),
            FAQItem(
                question = stringResource(R.string.question3),
                answer = stringResource(R.string.answer3)
            ),
            FAQItem(
                question = stringResource(R.string.question4),
                answer = stringResource(R.string.answer4)
            )
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier
                .fillMaxSize()
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
            .background(MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.medium)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = stringResource(R.string.question_icon),
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = item.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Text(
            text = item.answer,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}
