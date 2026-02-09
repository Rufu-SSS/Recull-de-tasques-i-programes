package com.example.PPH_tresenratlla_memory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.PPH_tresenratlla_memory.ui.theme.PapyrusFont

@Composable
fun MainScreen(
    onNavigateToTresEnRalla: () -> Unit = {},
    onNavigateToMemory: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Fons amb imatge de granja (hauràs de canviar fons.png per una imatge de granja)
        Image(
            painter = painterResource(id = R.drawable.fons),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Títol amb estil de granja
            Box {
                Text(
                    text = "Jocs de Granja",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PapyrusFont,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 32.dp),
                    style = TextStyle(
                        color = Color(0xFF3E2723), // Marró fosc
                        shadow = Shadow(
                            color = Color(0xFFFFEB3B), // Ombra groguenca
                            offset = Offset(6f, 6f),
                            blurRadius = 4f
                        )
                    )
                )

                Text(
                    text = "Jocs de Granja",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PapyrusFont,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 32.dp),
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFF8DC), // Beige clar
                                Color(0xFFDEB887)  // Marró sorra
                            )
                        )
                    )
                )
            }

            // Botó Tres en Ratlla amb colors de granja
            Button(
                onClick = onNavigateToTresEnRalla,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B4513) // Marró fusta
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(3.dp, Color(0xFF5D4037)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🐔 ",
                        fontSize = 32.sp
                    )
                    Text(
                        text = "3 en Ratlla",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PapyrusFont,
                        letterSpacing = 2.sp,
                        color = Color(0xFFFFF8DC),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0xFF3E2723),
                                offset = Offset(3f, 3f),
                                blurRadius = 2f
                            )
                        )
                    )
                }
            }

            // Botó Memory amb colors de granja
            Button(
                onClick = onNavigateToMemory,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6B8E23) // Verd olivera
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(3.dp, Color(0xFF556B2F)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🐄 ",
                        fontSize = 32.sp
                    )
                    Text(
                        text = "Memory",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PapyrusFont,
                        letterSpacing = 2.sp,
                        color = Color(0xFFFFF8DC),
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0xFF3E2723),
                                offset = Offset(3f, 3f),
                                blurRadius = 2f
                            )
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}