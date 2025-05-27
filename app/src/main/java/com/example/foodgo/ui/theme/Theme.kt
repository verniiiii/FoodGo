package com.example.foodgo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
private val DarkColorScheme = darkColorScheme(
    primary = Orange,             // основной цвет кнопок и акцентов
    onPrimary = Black,            // цвет текста на primary
    secondary = LiteOrange,       // вторичный цвет (например, для иконок/тегов)
    onSecondary = White,          // текст на secondary
    tertiary = Yeylow,            // дополнительный акцент
    onTertiary = IconGrey3,
    onSurfaceVariant = Green,
    background = IconGrey3,       // фон экрана
    onBackground = PlaceholderGrey,
    onSurface = GreyLight,
    error = Red,                  // ошибки
    surface = ProfGrey,
    onSecondaryContainer = DarkBlack
)

private val LightColorScheme = lightColorScheme(
    primary = Orange,             // основной цвет кнопок и акцентов
    onPrimary = White,            // цвет текста на primary
    secondary = LiteOrange,       // вторичный цвет (например, для иконок/тегов)
    onSecondary = Black,          // текст на secondary
    tertiary = Yeylow,            // дополнительный акцент
    onTertiary = DarkBlack,
    onSurfaceVariant = Green,
    background = GreyLight,       // фон экрана
    onBackground = ProfGrey,
    onSurface = IconGrey3,
    error = Red,                  // ошибки
    surface = PlaceholderGrey,
    onSecondaryContainer = IconGrey6,
    inverseOnSurface = GreyLight
)


@Composable
fun FoodGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}