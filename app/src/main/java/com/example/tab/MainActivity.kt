package com.example.tab

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tab.ui.theme.TabTheme
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
//class MainApplication: Application() {
//    override fun onCreate() {
//        super.onCreate()
//    }
//}

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PostViewModel> {
        ViewModelFactory(PostRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: PostViewModel) {
    val selectedTab = remember { mutableIntStateOf(0) }

    App()

//    TabScreen(selectedTab = selectedTab, viewModel)
}

class ScrollPositionState {
    private val scrollPositions = mutableMapOf<String, Int>()

    fun get(sectionName: String): Int {
        return scrollPositions[sectionName] ?: 0
    }

    fun set(sectionName: String, scrollOffset: Int) {
        scrollPositions[sectionName] = scrollOffset
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScrollableSection(sectionName: String, scrollState: ScrollState) {
    Scaffold(
        topBar = { Text(text = "Section: $sectionName") },
        content = {
            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                repeat(100) { index ->
                    Text(text = "Item $index", modifier = Modifier.padding(8.dp))
                }
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    var currentSection by remember { mutableStateOf("Section A") }
    val scrollStateA = rememberScrollState()
    val scrollStateB = rememberScrollState()

    Scaffold(
        content = {
            when (currentSection) {
                "Section A" -> ScrollableSection("Section A", scrollStateA)
                "Section B" -> ScrollableSection("Section B", scrollStateB)
                // Add more sections as needed
            }
        },
        bottomBar = {
            Row {
                Button(onClick = { currentSection = "Section A" }) {
                    Text(text = "Section A")
                }
                Button(onClick = { currentSection = "Section B" }) {
                    Text(text = "Section B")
                }
            }
        }
    )
}


//@Composable
//fun TabScreen(selectedTab: MutableState<Int>, viewModel: PostViewModel) {
//    val tabs = listOf("Tab 1", "Tab 2")
//
//    Column {
//        TabRow(selectedTabIndex = selectedTab.value) {
//            tabs.forEachIndexed { index, title ->
//                Tab(
//                    selected = selectedTab.value == index,
//                    onClick = { selectedTab.value = index }
//                ) {
//                    Text(text = title)
//                }
//            }
//        }
//
//        when (selectedTab.value) {
//            0 -> Tab1Screen(viewModel)
//            1 -> Tab2Screen(viewModel)
//        }
//    }
//}

@Composable
fun Tab1Screen(viewModel: PostViewModel) {
    LaunchedEffect(true) {
        viewModel.getPost()
    }

    val postList = viewModel.postList.collectAsState()
    Column() {
        Button(onClick = { viewModel.add() }) {
        }

        Button(onClick = {viewModel.setZero()}){}

        LazyColumn() {
            items(postList.value.size) { idx ->
                Text(postList.value[idx].title)
            }
        }
    }

}

@Composable
fun Tab2Screen(viewModel: PostViewModel) {
    val num = viewModel.num.collectAsState()
    val post = viewModel.post.collectAsState()
    val title = if(post.value == null) "비어있음" else post.value!!.title

        LaunchedEffect(true) {
            viewModel.getOnePost(num.value)
        }
    Column() {
        Text(num.value.toString())
        Text(title)
    }
}