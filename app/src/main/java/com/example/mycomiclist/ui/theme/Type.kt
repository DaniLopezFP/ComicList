package com.example.mycomiclist.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mycomiclist.R

val marvelFont = FontFamily(
    Font(R.font.marvelfamily, FontWeight.Light),
    Font(R.font.marvelfamily, FontWeight.Normal),
    Font(R.font.marvelfamily, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.marvelfamily, FontWeight.Medium),
    Font(R.font.marvelfamily, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)


/*
Font(R.font.marvelfamily, FontWeight.Normal),
Font(R.font.marvelfamily, FontWeight.Normal, FontStyle.Italic),
Font(R.font.fifaworldcup26, FontWeight.Medium),
Font(R.font.fifaworldcup26, FontWeight.Bold),
)*/
/* Other default text styles to override
titleLarge = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Normal,
fontSize = 22.sp,
lineHeight = 28.sp,
letterSpacing = 0.sp
),
labelSmall = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Medium,
fontSize = 11.sp,
lineHeight = 16.sp,
letterSpacing = 0.5.sp
)*/
