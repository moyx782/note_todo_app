package com.example.note_todo_app.ui.screens.ink

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.geometry.Offset
import java.util.*

// Data class to represent a page of ink strokes
data class InkPage(
    val id: String = UUID.randomUUID().toString(),
    val strokes: List<InkStroke> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PagingInkScreen(
    noteId: String?,
    navController: NavController,
    inkViewModel: InkViewModel // We'll define this later
) {
    val pagerState = rememberPagerState(pageCount = { inkViewModel.getPageCount() })
    
    var currentStroke by remember { mutableStateOf<InkStroke?>(null) }
    var currentPageIndex by remember { mutableStateInt(pagerState.currentPage) }
    var currentColor by remember { mutableStateOf(Color.Black) }
    var currentStrokeWidth by remember { mutableStateOf(5f) }
    var currentTool by remember { mutableStateOf(InkTool.PEN) }

    // Get current page's strokes
    val currentPageStrokes = inkViewModel.getInkPages()[currentPageIndex].strokes.toMutableList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("手写笔记 - 第${currentPageIndex + 1}页") },
                navigationIcon = {
                    IconButton(onClick = { 
                        // Save all pages before exiting
                        inkViewModel.saveAllPages(noteId)
                        navController.popBackStack() 
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Add new page
                        inkViewModel.addNewPage()
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "新增页面")
                    }
                    
                    IconButton(onClick = {
                        // Save the ink data and return to note
                        inkViewModel.saveAllPages(noteId)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.Done, contentDescription = "完成")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Toolbar for tools
            AdvancedInkToolbar(
                currentTool = currentTool,
                onToolChanged = { currentTool = it },
                currentColor = currentColor,
                onColorChanged = { currentColor = it },
                currentStrokeWidth = currentStrokeWidth,
                onStrokeWidthChanged = { currentStrokeWidth = it },
                onClearPressed = { 
                    inkViewModel.clearCurrentPage(currentPageIndex)
                },
                onUndoPressed = {
                    inkViewModel.undoOnPage(currentPageIndex)
                },
                onRedoPressed = {
                    inkViewModel.redoOnPage(currentPageIndex)
                },
                canUndo = inkViewModel.canUndoOnPage(currentPageIndex),
                canRedo = inkViewModel.canRedoOnPage(currentPageIndex)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Page indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${currentPageIndex + 1}/${pagerState.pageCount}")
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = { 
                        if (currentPageIndex > 0) {
                            inkViewModel.selectPage(currentPageIndex - 1)
                        }
                    },
                    enabled = currentPageIndex > 0
                ) {
                    Icon(Icons.Filled.NavigateBefore, contentDescription = "Previous page")
                }
                
                Button(
                    onClick = { 
                        if (currentPageIndex < pagerState.pageCount - 1) {
                            inkViewModel.selectPage(currentPageIndex + 1)
                        }
                    },
                    enabled = currentPageIndex < pagerState.pageCount - 1
                ) {
                    Icon(Icons.Filled.NavigateNext, contentDescription = "Next page")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Horizontal pager for pages
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { pageIndex ->
                // Canvas for drawing on each page
                DrawingCanvasWithPaging(
                    pageStrokes = inkViewModel.getInkPages()[pageIndex].strokes,
                    currentStroke = if (pageIndex == currentPageIndex) currentStroke else null,
                    currentTool = currentTool,
                    currentColor = currentColor,
                    currentStrokeWidth = currentStrokeWidth,
                    onNewPoint = { point ->
                        if (pageIndex == currentPageIndex) {
                            val newStroke = if (currentStroke == null) {
                                InkStroke(
                                    points = listOf(point),
                                    color = currentColor,
                                    strokeWidth = currentStrokeWidth,
                                    toolType = currentTool
                                )
                            } else {
                                currentStroke!!.copy(points = currentStroke!!.points + point)
                            }
                            currentStroke = newStroke
                        }
                    },
                    onStrokeEnd = {
                        if (currentPageIndex == pageIndex) {
                            currentStroke?.let { stroke ->
                                inkViewModel.addStrokeToPage(pageIndex, stroke)
                                currentStroke = null
                            }
                        }
                    },
                    onPageDelete = { index ->
                        inkViewModel.deletePage(index)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun DrawingCanvasWithPaging(
    pageStrokes: List<InkStroke>,
    currentStroke: InkStroke?,
    currentTool: InkTool,
    currentColor: Color,
    currentStrokeWidth: Float,
    onNewPoint: (Offset) -> Unit,
    onStrokeEnd: () -> Unit,
    onPageDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var startPressTime by remember { mutableLongStateOf(0L) }
    var isLongPressActive by remember { mutableStateOf(false) }
    
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Move -> {
                                val position = event.changes.first().position
                                onNewPoint(position)
                            }
                            PointerEventType.Press -> {
                                startPressTime = System.currentTimeMillis()
                                isLongPressActive = false
                                
                                // Start a coroutine to detect long press
                                kotlinx.coroutines.launch {
                                    kotlinx.coroutines.delay(500) // 500ms threshold for long press
                                    if (System.currentTimeMillis() - startPressTime >= 500) {
                                        isLongPressActive = true
                                    }
                                }
                            }
                            PointerEventType.Release -> {
                                if (isLongPressActive) {
                                    // Handle long press action (e.g., delete page)
                                    // This would typically trigger a confirmation dialog
                                }
                                onStrokeEnd()
                            }
                        }
                    }
                }
            }
    ) {
        // Draw all completed strokes on the page
        pageStrokes.forEach { stroke ->
            drawPath(
                path = stroke.points.toPath(),
                color = stroke.color,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke.strokeWidth)
            )
        }

        // Draw current stroke being drawn
        currentStroke?.let { stroke ->
            drawPath(
                path = stroke.points.toPath(),
                color = stroke.color,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke.strokeWidth)
            )
        }
    }
}