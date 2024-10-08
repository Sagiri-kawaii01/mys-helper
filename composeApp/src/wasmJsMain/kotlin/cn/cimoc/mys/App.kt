package cn.cimoc.mys

import CustomLightColors
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import androidx.compose.material.Typography
import cn.cimoc.mys.ext.hand

@Composable
fun App() {
    var typography by remember { mutableStateOf<Typography?>(null) }
    LaunchedEffect(Unit) {
        val font = loadCjkFont()
        typography = Typography(defaultFontFamily = font)
    }
    MaterialTheme(
        colors = CustomLightColors,
        typography = typography ?: MaterialTheme.typography
    ) {
        // 用于控制Drawer的状态
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                
                TopAppBar(
                    title = { Text("侧边栏示例") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                // 切换Drawer状态
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            },
                            modifier = Modifier.hand()
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Text("主内容区域")

            }
        }
    }
}

@OptIn(InternalResourceApi::class, InternalResourceApi::class)
suspend fun loadCjkFont(): FontFamily {

    val regular = readResourceBytes("font/LXGWWenKai-Regular.ttf")

    val medium = readResourceBytes("font/LXGWWenKai-Medium.ttf")

    val italic = readResourceBytes("font/LXGWWenKai-Light.ttf")

    return FontFamily(
        Font(identity = "LXGWWenKaiRegular", data = regular, weight = FontWeight.Normal),
        Font(identity = "LXGWWenKaiMedium", data = medium, weight = FontWeight.Medium),
        Font(identity = "LXGWWenKaiLight", data = italic, style = FontStyle.Italic),
    )

}