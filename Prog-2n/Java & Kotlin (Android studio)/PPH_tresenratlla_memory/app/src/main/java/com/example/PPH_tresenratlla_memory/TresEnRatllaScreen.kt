package com.example.PPH_tresenratlla_memory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.PPH_tresenratlla_memory.ui.theme.PapyrusFont
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class CellState {
    EMPTY, O, X
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TresEnRatllaScreen(onNavigateBack: () -> Unit = {}) {
    var board by remember { mutableStateOf(List(9) { CellState.EMPTY }) }
    var gameOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf<CellState?>(null) }
    var aiShouldMove by remember { mutableStateOf(false) }

    var wins by remember { mutableStateOf(0) }
    var losses by remember { mutableStateOf(0) }
    var ties by remember { mutableStateOf(0) }

    fun checkWinner(): CellState? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (pattern in winPatterns) {
            val (a, b, c) = pattern
            if (board[a] != CellState.EMPTY &&
                board[a] == board[b] &&
                board[b] == board[c]
            ) {
                return board[a]
            }
        }

        if (board.none { it == CellState.EMPTY }) {
            return CellState.EMPTY
        }

        return null
    }

    fun handlePlayerMove(index: Int) {
        if (board[index] == CellState.EMPTY && !gameOver && !aiShouldMove) {
            board = board.toMutableList().apply { this[index] = CellState.O }

            val result = checkWinner()
            if (result != null) {
                gameOver = true
                winner = result
                when (result) {
                    CellState.O -> wins++
                    CellState.X -> losses++
                    else -> ties++
                }
            } else {
                aiShouldMove = true
            }
        }
    }

    LaunchedEffect(aiShouldMove) {
        if (aiShouldMove) {
            delay(200)
            board = aiMove(board)
            val aiResult = checkWinner()
            if (aiResult != null) {
                gameOver = true
                winner = aiResult
                when (aiResult) {
                    CellState.O -> wins++
                    CellState.X -> losses++
                    else -> ties++
                }
            }
            aiShouldMove = false
        }
    }

    fun resetGame() {
        board = List(9) { CellState.EMPTY }
        gameOver = false
        winner = null
        aiShouldMove = false
    }

    fun resetScore() {
        wins = 0
        losses = 0
        ties = 0
        resetGame()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAED581)) // Verd prat
    ) {
        TopAppBar(
            title = { Text("🐔 Tres en Ratlla", fontFamily = PapyrusFont, letterSpacing = 2.sp) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Tornar al Menú"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF8B4513), // Marró fusta
                titleContentColor = Color(0xFFFFF8DC),
                navigationIconContentColor = Color(0xFFFFF8DC)
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreItem(label = "Victòries 🏆", value = wins, color = Color(0xFFFFD700))
                ScoreItem(label = "Empats 🤝", value = ties, color = Color(0xFFFFA726))
                ScoreItem(label = "Derrotes 😢", value = losses, color = Color(0xFFE57373))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tauler amb aspecte de fusta
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .aspectRatio(1f)
                    .background(Color(0xFFD2691E), RoundedCornerShape(8.dp)) // Marró xocolata
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val cellSize = canvasWidth / 3
                    val woodColor = Color(0xFF8B4513) // Marró fusta

                    // Línies verticals
                    drawLine(
                        color = woodColor,
                        start = Offset(cellSize, 0f),
                        end = Offset(cellSize, canvasHeight),
                        strokeWidth = 12f,
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        color = woodColor,
                        start = Offset(cellSize * 2, 0f),
                        end = Offset(cellSize * 2, canvasHeight),
                        strokeWidth = 12f,
                        cap = StrokeCap.Round
                    )

                    // Línies horitzontals
                    drawLine(
                        color = woodColor,
                        start = Offset(0f, cellSize),
                        end = Offset(canvasWidth, cellSize),
                        strokeWidth = 12f,
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        color = woodColor,
                        start = Offset(0f, cellSize * 2),
                        end = Offset(canvasWidth, cellSize * 2),
                        strokeWidth = 12f,
                        cap = StrokeCap.Round
                    )
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.weight(1f)) {
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[0],
                            onClick = { handlePlayerMove(0) }
                        )
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[1],
                            onClick = { handlePlayerMove(1) }
                        )
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[2],
                            onClick = { handlePlayerMove(2) }
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[3],
                            onClick = { handlePlayerMove(3) }
                        )
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[4],
                            onClick = { handlePlayerMove(4) }
                        )
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[5],
                            onClick = { handlePlayerMove(5) }
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[6],
                            onClick = { handlePlayerMove(6) }
                        )
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[7],
                            onClick = { handlePlayerMove(7) }
                        )
                        GameButton(
                            modifier = Modifier.weight(1f),
                            cellState = board[8],
                            onClick = { handlePlayerMove(8) }
                        )
                    }
                }
            }
        }

        if (gameOver) {
            val message = when (winner) {
                CellState.O -> "Has Guanyat! 🎉🐓"
                CellState.X -> "Ha Guanyat la IA! 🤖"
                else -> "Empat! 🤝"
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 140.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = message,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PapyrusFont,
                    color = Color(0xFF3E2723),
                    letterSpacing = 2.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xFFFFEB3B),
                            offset = Offset(4f, 4f),
                            blurRadius = 3f
                        )
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp, start = 12.dp, end = 12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                Button(
                    onClick = { resetGame() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B8E23) // Verd olivera
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(3.dp, Color(0xFF556B2F)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "🔄 Nova Partida",
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

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { resetScore() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B4513) // Marró fusta
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(3.dp, Color(0xFF5D4037)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "↻ Reiniciar Marcador",
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
}

@Composable
fun ScoreItem(label: String, value: Int, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
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
            text = label,
            fontSize = 12.sp,
            fontFamily = PapyrusFont,
            color = Color(0xFF5D4037),
            letterSpacing = 1.sp
        )
    }
}

fun aiMove(board: List<CellState>): List<CellState> {
    val emptyIndices = board.indices.filter { board[it] == CellState.EMPTY }

    if (emptyIndices.isEmpty()) {
        return board
    }

    val randomIndex = emptyIndices[Random.nextInt(emptyIndices.size)]
    return board.toMutableList().apply { this[randomIndex] = CellState.X }
}

@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    cellState: CellState,
    onClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when (cellState) {
            CellState.O -> {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.o),
                    contentDescription = "O"
                )
            }

            CellState.X -> {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.x),
                    contentDescription = "X"
                )
            }

            CellState.EMPTY -> {
            }
        }
    }
}

@Preview
@Composable
fun TresEnRatllaScreenPreview() {
    TresEnRatllaScreen()
}