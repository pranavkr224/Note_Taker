package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.notes.ui.theme.NotesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StickyNotesApp()
                }
            }
        }
    }
}

data class Note(val title: String, val content: String)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickyNotesApp() {
    val notes = remember { mutableStateListOf<Note>() }
    var isVisible by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }


    // Moving Button up when Keyboard appears


    Box(

        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        if (isVisible) {
            print("o")
        } else {
            LazyColumn {
                items(notes) { note ->
                    Notes_card(note = note, onDelete = { notes.remove(note) })
                }
            } // Have to use notes_card function here
        }


        // Text field and save button ----------------------------------------

        if (isVisible) {


            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .padding(32.dp)

            ) {

                // Text field for saving text------------------------------------------
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Notes") },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)


        ) {


            // Add icon button
            if (isVisible) {
                IconButton(
                    onClick = {
                        // Add a new note to the list
                        notes.add(Note("Title", text))
                        text = ""
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(24))
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )

                }
            }


            Spacer(modifier = Modifier.width(24.dp))
            // Edit button----------------------------------------------------------
            IconButton(
                onClick = { isVisible = !isVisible },
                modifier = Modifier
                    .clip(RoundedCornerShape(24))
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.primary)

            ) {
                if (isVisible) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }

            }
        }
    }
}


@Composable
fun Notes_card(note: Note, onDelete: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)

    ) {
        Text(text = note.content)
        IconButton(
            onClick = onDelete,
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable

fun Show() {
    NotesTheme {
        StickyNotesApp()
    }
}




