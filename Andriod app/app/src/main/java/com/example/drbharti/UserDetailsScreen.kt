package com.example.drbharti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drbharti.ui.theme.DrBhartiTheme
import com.example.drbharti.ui.theme.InputBackground
import com.example.drbharti.ui.theme.PrimaryGreen
import com.example.drbharti.ui.theme.TextPrimary
import com.example.drbharti.viewmodel.AppViewModel

@Composable
fun UserDetailsScreen(
    onNavigateBack: () -> Unit,
    onSubmit: () -> Unit,
    viewModel: AppViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    
    // Get initial values from the viewModel if available
    var name by remember { mutableStateOf(viewModel.registrationData.name) }
    var selectedGender by remember { mutableStateOf(viewModel.registrationData.gender) }
    var dateOfBirth by remember { mutableStateOf(viewModel.registrationData.dateOfBirth) }
    var age by remember { mutableStateOf(viewModel.registrationData.age) }
    var height by remember { mutableStateOf(viewModel.registrationData.height) }
    var weight by remember { mutableStateOf(viewModel.registrationData.weight) }
    var allergies by remember { mutableStateOf(viewModel.registrationData.allergies) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Let's Begin Text
        Text(
            text = "Let's Begin",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Name Field
        Text(
            text = "Name",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = name,
            onValueChange = { 
                name = it
                viewModel.updateRegistrationData(name = it)
            },
            placeholder = { Text("Enter your Name") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(InputBackground),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Gender Selection
        Text(
            text = "Gender",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        val genderOptions = listOf("Female", "Male", "Other")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .padding(bottom = 16.dp)
        ) {
            genderOptions.forEach { gender ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = selectedGender == gender,
                            onClick = { 
                                selectedGender = gender
                                viewModel.updateRegistrationData(gender = gender)
                            },
                            role = Role.RadioButton
                        )
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = PrimaryGreen
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        // Date of Birth and Age
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Date of Birth",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = { 
                        dateOfBirth = it
                        viewModel.updateRegistrationData(dateOfBirth = it)
                    },
                    placeholder = { Text("DD-MM-YYYY") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(InputBackground),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = PrimaryGreen,
                        cursorColor = TextPrimary
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "Calendar",
                            tint = PrimaryGreen
                        )
                    }
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Age",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                OutlinedTextField(
                    value = age,
                    onValueChange = { 
                        age = it
                        viewModel.updateRegistrationData(age = it)
                    },
                    placeholder = { Text("Age") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(InputBackground),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = PrimaryGreen,
                        cursorColor = TextPrimary
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
        }
        
        // Height Field
        Text(
            text = "Height",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = height,
            onValueChange = { 
                height = it
                viewModel.updateRegistrationData(height = it)
            },
            placeholder = { Text("Enter your height") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(InputBackground),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Weight Field
        Text(
            text = "Weight",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = weight,
            onValueChange = { 
                weight = it
                viewModel.updateRegistrationData(weight = it)
            },
            placeholder = { Text("Enter your Weight") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(InputBackground),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Allergies Field
        Text(
            text = "Allergy / Disease(if any )",
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = allergies,
            onValueChange = { 
                allergies = it
                viewModel.updateRegistrationData(allergies = it)
            },
            placeholder = { Text("Enter") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(InputBackground),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.LightGray,
                focusedIndicatorColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Submit Button
        Button(
            onClick = { 
                try {
                    // Register the user with all collected data
                    viewModel.register()
                    viewModel.navigateToNextScreen()
                    onSubmit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview() {
    DrBhartiTheme {
        UserDetailsScreen(
            onNavigateBack = {},
            onSubmit = {}
        )
    }
}
