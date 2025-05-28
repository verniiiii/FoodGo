package com.example.foodgo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodgo.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onGetStartedClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(R.drawable.s1, stringResource(R.string.onbording1)),
        OnboardingPage(R.drawable.s2, stringResource(R.string.onbording2)),
        OnboardingPage(R.drawable.s3, stringResource(R.string.onbording3))
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(140.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState
            ) { page ->
                Image(
                    painter = painterResource(id = pages[page].imageRes),
                    contentDescription = "Onboarding Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )

            }
        }

        Spacer(modifier = Modifier.height(89.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pages[pagerState.currentPage].title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                modifier = Modifier.width(327.dp),
                text = stringResource(R.string.onbording4),
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(pages.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 11.dp)
                        .size(if (index == pagerState.currentPage) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else Color.Gray.copy(
                                alpha = 0.5f
                            )
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(69.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < pages.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }else{
                        onGetStartedClick()
                    }
                }
            },
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .height(62.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = if (pagerState.currentPage < pages.lastIndex) stringResource(R.string.next) else stringResource(R.string.go),
                color = MaterialTheme.colorScheme.onPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (pagerState.currentPage < pages.lastIndex) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.skip),
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 40.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pages.lastIndex)
                        }
                    }
            )
        }else{
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}

data class OnboardingPage(
    val imageRes: Int,
    val title: String
)




