package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.models.DaoSession;
import com.example.notesapp.models.Note;
import com.example.notesapp.models.NoteDao;
import com.example.notesapp.repository.NotesRepository;
import com.example.notesapp.repository.RepositoryProvider;
import com.example.notesapp.repository.Tags;

public class AddNoteActivity extends AppCompatActivity {

    public static final int NO_NOTE = -1;
    private EditText titleEditText;
    private EditText descriptionEditText;

    private NotesRepository notesRepository;
    private long noteId;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        notesRepository = RepositoryProvider.getInstance(this);
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveNote());
        noteId = getIntent().getLongExtra(Tags.NOTE_ID, NO_NOTE);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        if (noteId != NO_NOTE) {
            note = notesRepository.geyById(noteId);
            updateUi(note);
        }

    }

    private void updateUi(Note note) {
        if (note != null) {
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDescription());

        }
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if (note == null) {
            note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            notesRepository.add(note);
        } else {
            note.setTitle(title);
            note.setDescription(description);
            notesRepository.edit(note);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();

    }
}