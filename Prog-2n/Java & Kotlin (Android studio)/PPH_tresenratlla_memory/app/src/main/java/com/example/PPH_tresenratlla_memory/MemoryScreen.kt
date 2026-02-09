package com.example.PPH_tresenratlla_memory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
import com.example.PPH_tresenratlla_memory.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryScreen(onNavigateBack: () -> Unit = {}) {
    val coroutineScope = rememberCoroutineScope()

    var tauler by remember { mutableStateOf(List(16) { 0 }) }
    var visibleCards by remember { mutableStateOf(List(16) { false }) }
    var matchedCards by remember { mutableStateOf(List(16) { false }) }
    var primer by remember { mutableStateOf<Int?>(null) }
    var numPrimer by remember { mutableStateOf(0) }
    var bloc by remember { mutableStateOf(false) }
    var showInitialCards by remember { mutableStateOf(true) }
    var puntuacio by remember { mutableStateOf(0) }
    var intents by remember { mutableStateOf(0) }

    val img = remember {
        intArrayOf(
            R.drawable.la0,
            R.drawable.la1,
            R.drawable.la2,
            R.drawable.la3,
            R.drawable.la4,
            R.drawable.la5,
            R.drawable.la6,
            R.drawable.la7
        )
    }
    val fons = R.drawable.fondo

    fun iniciarPartida() {
        coroutineScope.launch {
            showInitialCards = true
            visibleCards = List(16) { false }
            matchedCards = List(16) { false }

            delay(50)

            val arrayDesordre = mesclar(img.size)
            tauler = arrayDesordre
            primer = null
            numPrimer = 0
            bloc = false
            puntuacio = 0
            intents = 0

            visibleCards = List(16) { true }
            delay(1500)

            visibleCards = List(16) { false }
            showInitialCards = false
        }
    }

    LaunchedEffect(Unit) {
        iniciarPartida()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAED581))
    ) {
        TopAppBar(
            title = { Text("🐄 Memory Granja", fontFamily = PapyrusFont, letterSpacing = 2.sp) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Tornar al Menú"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF8B4513),
                titleContentColor = Color(0xFFFFF8DC),
                navigationIconContentColor = Color(0xFFFFF8DC)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 100.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 128.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = puntuacio.toString(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PapyrusFont,
                        color = Color(0xFF3E2723),
                        letterSpacing = 2.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0xFFFFEB3B),
                                offset = Offset(3f, 3f),
                                blurRadius = 2f
                            )
                        )
                    )
                    Text(
                        text = "🌾 Puntuació",
                        fontSize = 14.sp,
                        fontFamily = PapyrusFont,
                        color = Color(0xFF5D4037),
                        letterSpacing = 2.sp
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = intents.toString(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PapyrusFont,
                        color = Color(0xFF3E2723),
                        letterSpacing = 2.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0xFFFFEB3B),
                                offset = Offset(3f, 3f),
                                blurRadius = 2f
                            )
                        )
                    )
                    Text(
                        text = "🎯 Intents",
                        fontSize = 14.sp,
                        fontFamily = PapyrusFont,
                        color = Color(0xFF5D4037),
                        letterSpacing = 2.sp
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(tauler) { index, cardValue ->
                    MemoryCard(
                        cardValue = cardValue,
                        isVisible = visibleCards[index] || matchedCards[index],
                        isMatched = matchedCards[index],
                        img = img,
                        fons = fons,
                        enabled = !bloc && !showInitialCards && !matchedCards[index],
                        onClick = {
                            if (!bloc && !visibleCards[index] && !matchedCards[index]) {
                                if (primer == null) {
                                    primer = index
                                    numPrimer = tauler[index]
                                    visibleCards =
                                        visibleCards.toMutableList().apply { set(index, true) }
                                } else {
                                    bloc = true
                                    val numSegon = tauler[index]
                                    visibleCards =
                                        visibleCards.toMutableList().apply { set(index, true) }

                                    if (numPrimer == numSegon) {
                                        matchedCards = matchedCards.toMutableList().apply {
                                            set(primer!!, true)
                                            set(index, true)
                                        }
                                        puntuacio += 10
                                        intents++
                                        primer = null
                                        bloc = false
                                    } else {
                                        puntuacio -= 2
                                        intents++
                                        val primerIndex = primer!!
                                        coroutineScope.launch {
                                            delay(1000)
                                            visibleCards = visibleCards.toMutableList().apply {
                                                set(primerIndex, false)
                                                set(index, false)
                                            }
                                            primer = null
                                            bloc = false
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp, start = 12.dp, end = 12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { iniciarPartida() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6B8E23)
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(3.dp, Color(0xFF556B2F)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "🔄 Reiniciar",
                    fontSize = 20.sp,
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

@Composable
fun MemoryCard(
    cardValue: Int,
    isVisible: Boolean,
    isMatched: Boolean,
    img: IntArray,
    fons: Int,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val imageResource = if (isVisible || isMatched) img[cardValue] else fons

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = if (isMatched) Color(0xFFFFD54F) else Color(0xFFD2691E),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = enabled) { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Memory card",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

private fun mesclar(longitud: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (i in 0 until longitud) {
        result.add(i)
        result.add(i)
    }
    return result.shuffled()
}

@Preview
@Composable
fun MemoryScreenPreview() {
    MemoryScreen()
}