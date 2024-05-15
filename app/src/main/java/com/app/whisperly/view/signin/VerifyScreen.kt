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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.whisperly.util.Countries
import com.app.whisperly.view.navigation.NavRoute
import androidx.hilt.navigation.compose.hiltViewModel


object VerifyRoute : NavRoute<SignInViewModel> {

    override val route = "verifyScreen/"

    @Composable
    override fun viewModel(): SignInViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: SignInViewModel) = VerifyScreen(viewModel)
}
@Composable
fun VerifyScreen(
    viewModel: SignInViewModel
) {
    // Replace with your actual state management logic
    val one by viewModel.otpOne.observeAsState("")
    val two by viewModel.otpTwo.observeAsState("")
    val three by viewModel.otpThree.observeAsState("")
    val four by viewModel.otpFour.observeAsState("")
    val five by viewModel.otpFive.observeAsState("")
    val six by viewModel.otpSix.observeAsState("")

    // Focus requesters for each OTP input
    val focusRequesterOne = remember { FocusRequester() }
    val focusRequesterTwo = remember { FocusRequester() }
    val focusRequesterThree = remember { FocusRequester() }
    val focusRequesterFour = remember { FocusRequester() }
    val focusRequesterFive = remember { FocusRequester() }
    val focusRequesterSix = remember { FocusRequester() }

    val country = viewModel.country.observeAsState()
    val phoneNumber by viewModel.mobile.observeAsState()

    val authResult by viewModel.getTaskResult().observeAsState()

    authResult?.let {
        viewModel.fetchUser(it)
    }

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
            text = "Enter your OTP sent to $phoneNumber",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        //OTP fields
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            OutlinedTextField(
                value = one,
                onValueChange = {
                    viewModel.otpOne.value = it
                    if (it.length == 1) focusRequesterTwo.requestFocus()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .weight(0.16f)
                    .align(Alignment.CenterVertically)
                    .focusRequester(focusRequesterOne)
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = two,
                onValueChange = {
                    viewModel.otpTwo.value = it
                    if (it.length == 1) focusRequesterThree.requestFocus()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .weight(0.16f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
                    .focusRequester(focusRequesterTwo)
            )
            OutlinedTextField(
                value = three,
                onValueChange = {
                    viewModel.otpThree.value = it
                    if (it.length == 1) focusRequesterFour.requestFocus()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .weight(0.16f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
                    .focusRequester(focusRequesterThree)
            )
            OutlinedTextField(
                value = four,
                onValueChange = {
                    viewModel.otpFour.value = it
                    if (it.length == 1) focusRequesterFive.requestFocus()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .weight(0.16f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
                    .focusRequester(focusRequesterFour)
            )
            OutlinedTextField(
                value = five,
                onValueChange = {
                    viewModel.otpFive.value = it
                    if (it.length == 1) focusRequesterSix.requestFocus()
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .weight(0.16f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
                    .focusRequester(focusRequesterFive)
            )
            OutlinedTextField(
                value = six,
                onValueChange = {
                    viewModel.otpSix.value = it
                    viewModel.validateOtp()
                                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .weight(0.16f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
                    .focusRequester(focusRequesterSix)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {viewModel.validateOtp()},
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Continue")
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}