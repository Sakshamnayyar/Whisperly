package com.app.whisperly.view.homescreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.whisperly.db.data.ChatUser
import com.app.whisperly.model.Contact
import com.app.whisperly.view.NewContactsDisplayActivity
import com.app.whisperly.view.navigation.NavRoute
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KProperty0


object HomeScreenRoute : NavRoute<HomeScreenViewModel> {

    override val route = "homeScreen/"

    @Composable
    override fun viewModel(): HomeScreenViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: HomeScreenViewModel) = HomeScreen(viewModel)
}
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {
    val chats = viewModel.chatUserList
    val context = LocalContext.current

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    Scaffold (
        topBar = { TopApplicationBar() },
        floatingActionButton = { FloatingActionButtonWithDropdownMenu(navController, scope) },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = { BottomApplicationBar(navController, scope) }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Chats.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Chats.route) {
                // Composable for the Chats screen
            }
            composable(Screen.Calls.route) {
                // Composable for the Calls screen
            }
            composable(Screen.Profile.route) {
                //TODO: Temporary sign out
                FirebaseAuth.getInstance().signOut()

            }
        }
        Box (
            modifier = Modifier
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter,
        ){
            HomeScreenList(chats = chats)
        }

    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApplicationBar(){
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Wisperly", color = MaterialTheme.colorScheme.onPrimary)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More options", tint = Color.White)
            }
        }
    )
}

@Composable
fun BottomApplicationBar(navController: NavHostController, scope: CoroutineScope) {
    val bottomBarItems = listOf(
        Screen.Chats,
        Screen.Calls,
        Screen.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        bottomBarItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = screen.route == "chats", // Replace with current route check
                onClick = {
                    // Handle navigation
                    navController.navigate(screen.route)

                }
            )
        }
    }
}

@Composable
fun FloatingActionButtonWithDropdownMenu(navController: NavHostController, scope: CoroutineScope) {
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the result from NewContactsDisplayActivity
            val returnedData = result.data?.getParcelableExtra("resultKey") as Contact?
//            val returnedData = Contact("hello","1234567897")
            Toast.makeText(context, "Result: $returnedData", Toast.LENGTH_SHORT).show()
        }
    }

    var contactsPermissionGranted by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, proceed to fetch and display contacts
            contactsPermissionGranted = true
        } else {
            // Handle the case where permission is denied
        }
    }

    Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
        FloatingActionButton(onClick = { showMenu = true }) {
            Icon(Icons.Filled.Add, contentDescription = "Options")
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier.padding(top = 5.dp) // Adjust this value to position the menu below the FAB
        ) {
            // Replace these with your actual options
            DropdownMenuItem(onClick = {
                //Open Contacts from phone.
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                //open NewContactsDisplayActivity
                val intent = Intent(context, NewContactsDisplayActivity::class.java)
                startForResult.launch(intent)
            }) {
                Text("Get Contacts")
            }
        }
    }
}

@Composable
fun HomeScreenList(chats: List<ChatUser>) {
    Column {
        Text(text = "List of chats will be shown below")
        LazyColumn {
            items(chats) { chat ->
//                ChatUserCard(chat)
            }
        }
    }
}


// Define your navigation screens as objects within this enum class
enum class Screen(val route: String, val icon: ImageVector, val title: String) {
    Chats("chats", Icons.Default.Send, "Chats"),
    //    Status("status", Icons.Default.CameraAlt, "Status"),
    Calls("calls", Icons.Default.Call, "Calls"),
    Profile("profile", Icons.Default.AccountCircle, "Profile")
    // Add more screens as needed
}
