package com.app.whisperly.view.signin

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.whisperly.util.Countries
import com.app.whisperly.view.navigation.NavRoute
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.app.whisperly.R


object SignInRoute : NavRoute<SignInViewModel> {

    override val route = "signInScreen/"

    @Composable
    override fun viewModel(): SignInViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SignInViewModel) = SignInScreen(viewModel)
}

@Composable
fun SignInScreen(
    viewModel: SignInViewModel
) {
    // Replace with your actual state management logic
    var text by remember { mutableStateOf("") }
    val phoneNumber by viewModel.mobile.observeAsState("")
    val countryCode by viewModel.country.observeAsState()
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "WHISPERLY MESSAGING APPLICATION",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Tell us your mobile number",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Country dropdown
            OutlinedTextField(
                modifier = Modifier
                    .weight(0.3f)
                    .align(Alignment.CenterVertically)
                    .padding(top = 8.dp),
                value = countryCode!!.noCode,
                onValueChange = {},
                readOnly = true, // Make the TextField read-only,
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        "Dropdown",
                        Modifier
                            .clickable { expanded = !expanded }
                            .width(IntrinsicSize.Min))
                },
                singleLine = true
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(IntrinsicSize.Min)
            ) {
                Countries.getCountries().forEach { country ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        viewModel.country.value = country
                    }) {
                        Text(country.name)
                    }
                }
            }
            //PhoneNumberField
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    viewModel.mobile.value = it
                                },
                label = { Text("Mobile Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .weight(0.7f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                //TODO: Check if number is valid or not
                //TODO: Check if device is connected to internet or not
                viewModel.setMobile(context)
                viewModel.startVerification()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Continue")
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

