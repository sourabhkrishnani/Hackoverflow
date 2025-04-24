package com.example.scheduleassistant

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TaskDetailScreen(){

    var check by rememberSaveable { mutableStateOf(true) }
    val titleTxt = "Read 10 pages of a book"

}