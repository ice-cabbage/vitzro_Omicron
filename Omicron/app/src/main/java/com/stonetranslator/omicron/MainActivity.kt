package com.stonetranslator.omicron

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.stonetranslator.omicron.ui.theme.OmicronTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OmicronTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainCompose()
                }
            }
        }
        if (!isConnected()) {
            Log.d(this.javaClass.name, "Internet connection NOT available")
            Toast.makeText(applicationContext, "Internet connection NOT available", Toast.LENGTH_LONG).show()
        }
    }

    private fun isConnected(): Boolean {
        var result = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null) {
                result = when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
        } else {
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                // connected to the internet
                result = when (activeNetwork.type) {
                    ConnectivityManager.TYPE_WIFI,
                    ConnectivityManager.TYPE_MOBILE,
                    ConnectivityManager.TYPE_VPN -> true
                    else -> false
                }
            }
        }
        return result
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.mainGraph(drawerState: DrawerState, navController: NavHostController) {
    navigation(startDestination = "home", route = NavRoutes.MainRoute.name) {
        composable("home") {
            HomeScreen()
        }
        composable("system") {
            SystemScreen()
        }
        composable("true") {
            TrueScreen()
        }
        composable("sequential") {
            SequentialScreen()
        }
        composable("demand") {
            DemandScreen()
        }
        composable("reverse") {
            ReverseScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
        composable("about") {
            AboutScreen()
        }
    }
}

data class AppDrawerItemInfo(
    val name: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    var argument: String? = null,
)

object DrawerParams {
    val drawerItems = arrayListOf(
        AppDrawerItemInfo(
            "home",
            R.string.home,
            R.drawable.ic_home,
            R.string.home_description
        ),
        AppDrawerItemInfo(
            "system",
            R.string.system,
            R.drawable.ic_system,
            R.string.system_description
        ),
        AppDrawerItemInfo(
            "true",
            R.string.sincere,
            R.drawable.ic_true,
            R.string.sincere_description
        ),
        AppDrawerItemInfo(
            "sequential",
            R.string.sequential,
            R.drawable.ic_sequential,
            R.string.sequential_description
        ),
        AppDrawerItemInfo(
            "demand",
            R.string.demand,
            R.drawable.ic_demand,
            R.string.demand_description
        ),
        AppDrawerItemInfo(
            "reverse",
            R.string.reverse,
            R.drawable.ic_reverse,
            R.string.reverse_description
        )
    )
}

enum class NavRoutes {
    MainRoute,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var route = currentBackStackEntry?.destination?.route.toString().substringBeforeLast("/")
    var title = ""
    DrawerParams.drawerItems.forEach{
        if (it.name == route)
            title = stringResource(it.title)
    }
    if (route == "settings")
        title = stringResource(R.string.settings)
    else if (route == "about")
        title = stringResource(R.string.about)
    Surface {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                val coroutineScope = rememberCoroutineScope()
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(225.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.width(225.dp),
                        ) {
                            Column {
                                Box(Modifier.clip(CircleShape)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_background),
                                        contentDescription = "Icon",
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = "Icon",
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(DrawerParams.drawerItems) {
                                Surface(
                                    color = if (route == it.name)
                                        MaterialTheme.colorScheme.primary else
                                        MaterialTheme.colorScheme.background,
                                    modifier = Modifier.width(225.dp),
                                    onClick = {
                                        coroutineScope.launch {
                                            drawerState.close()
                                        }
                                        if (route != it.name) {
                                            route = it.name
                                            if (it.argument != null)
                                                route += "/" + it.argument
                                            navController.navigate(route) {
                                                popUpTo(NavRoutes.MainRoute.name)
                                            }
                                        }
                                    },
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = it.icon),
                                            contentDescription = stringResource(id = it.description),
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = stringResource(id = it.title),
                                            style = MaterialTheme.typography.bodyMedium,
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                title,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        },
                        navigationIcon = {
                            val coroutineScope = rememberCoroutineScope()
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    if (route == "settings" || route == "about") {
                                        navController.popBackStack()
                                    } else {
                                        drawerState.open()
                                    }
                                }
                            }) {
                                if (route == "settings" || route == "about") {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        contentDescription = stringResource(id = R.string.back)
                                    )
                                } else {
                                    Icon(
                                        Icons.Rounded.Menu,
                                        stringResource(id = R.string.menu_description),
                                        Modifier.size(24.dp),
                                        MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                            }
                        },
                        actions = {
                            if (route != "settings" && route != "about") {
                                IconButton(onClick = {
                                    navController.navigate("settings") {
                                        popUpTo(popUpToId)
                                    }
                                }) {
                                    Icon(
                                        painterResource(R.drawable.ic_settings),
                                        stringResource(R.string.settings_description),
                                        Modifier.size(24.dp),
                                        MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                                IconButton(onClick = {
                                    navController.navigate("about") {
                                        popUpTo(popUpToId)
                                    }
                                }) {
                                    Icon(
                                        painterResource(R.drawable.ic_about),
                                        stringResource(R.string.about_description),
                                        Modifier.size(24.dp),
                                        MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            MaterialTheme.colorScheme.primary
                        ),
                    )
                },
                bottomBar = {
                    NavigationBar() {
                        NavigationBarItem(route == "home",
                            onClick = {
                                navController.navigate("home") {
                                    popUpTo(popUpToId)
                                }
                            },
                            icon = { Icon(
                                painterResource(R.drawable.ic_home),
                                contentDescription = stringResource(R.string.home_description),
                                modifier = Modifier.size(32.dp))
                            },
                            label = {Text(stringResource(R.string.home))}
                        )
                        NavigationBarItem(route == "system",
                            onClick = {
                                navController.navigate("system") {
                                    popUpTo(popUpToId)
                                }
                            },
                            icon = { Icon(
                                painterResource(R.drawable.ic_system),
                                contentDescription = stringResource(R.string.system_description),
                                modifier = Modifier.size(32.dp))
                            },
                            label = {Text(stringResource(R.string.system))}
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .wrapContentSize(Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NavHost(
                        navController,
                        startDestination = NavRoutes.MainRoute.name
                    ) {
                        mainGraph(drawerState, navController)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OmicronTheme {
        MainCompose()
    }
}