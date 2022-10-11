package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.models.Note;
import com.example.notesapp.repository.NotesRepository;
import com.example.notesapp.repository.RepositoryProvider;
import com.example.notesapp.repository.Tags;

public class AddNoteActivity extends AppCompatActivity {

    public static final int NO_NOTE = -1;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private CheckBox archiveCheckBox;


    private NotesRepository notesRepository;
    private long noteId;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        notesRepository = RepositoryProvider.getInstance(this);
        Button saveButton = findViewById(R.id.signUpButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(v -> saveNote());

        noteId = getIntent().getLongExtra(Tags.NOTE_ID, NO_NOTE);

        titleEditText = findViewById(R.id.usernameEditText);
        descriptionEditText = findViewById(R.id.passwordEditText);
        archiveCheckBox = findViewById(R.id.archiveCheckBox);

        if (noteId != NO_NOTE) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> deleteNote(noteId));
            note = notesRepository.getNoteById(noteId);
            updateUi(note);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }

    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (note == null) {
            note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            notesRepository.addNote(note);
        } else {
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            notesRepository.editNote(note);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void deleteNote(long noteId) {
        notesRepository.deleteNote(noteId);
        Toast.makeText(this, "Note Obliterated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateUi(Note note) {
        if (note != null) {
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());
            archiveCheckBox.setChecked(note.getArchived());
        }
    }
}