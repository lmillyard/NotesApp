package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.adapters.NoteColourAdapter;
import com.example.notesapp.models.Note;
import com.example.notesapp.models.NoteColour;
import com.example.notesapp.repository.NotesRepository;
import com.example.notesapp.repository.RepositoryProvider;
import com.example.notesapp.repository.Tags;
import com.example.notesapp.viewmodels.NoteColourViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    public static final int NO_NOTE = -1;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private CheckBox archiveCheckBox;


    private NotesRepository notesRepository;
    private long noteId;
    private Note note;
    private NoteColourAdapter noteColourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        notesRepository = RepositoryProvider.getInstance(this);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(v -> saveNote());

        noteId = getIntent().getLongExtra(Tags.NOTE_ID, NO_NOTE);

        titleEditText = findViewById(R.id.usernameEditText);
        descriptionEditText = findViewById(R.id.passwordEditText);
        archiveCheckBox = findViewById(R.id.archiveCheckBox);

        List<NoteColour> noteColours = notesRepository.getNoteColours();
        List<NoteColourViewModel> viewModels = createViewModels(noteColours);

        if (noteId != NO_NOTE) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> deleteNote(noteId));
            note = notesRepository.getNoteById(noteId);
            selectNoteColour(note, viewModels);
            updateUi(note);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }

        RecyclerView recyclerView = findViewById(R.id.noteColourRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        noteColourAdapter = new NoteColourAdapter(this, viewModels);
        recyclerView.setAdapter(noteColourAdapter);
    }

    private void selectNoteColour(Note note, List<NoteColourViewModel> viewModels) {
        for (NoteColourViewModel noteColourVM: viewModels) {
            if(note.getColourId() == noteColourVM.getNoteColour().getId()) {
                noteColourVM.setSelected(true);
                break;
            }
        }
    }

    private List<NoteColourViewModel> createViewModels(List<NoteColour> noteColours) {
        List<NoteColourViewModel> list = new ArrayList<>();
        for (NoteColour noteColour: noteColours) {
            list.add(new NoteColourViewModel(noteColour));
        }
        return list;
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

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        NoteColourViewModel selectedColour = noteColourAdapter.getSelectedColour();
        if(selectedColour == null) {
            Toast.makeText(this, "Please pick a colour", Toast.LENGTH_SHORT).show();
            return;
        }

        if (note == null) {
            note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            note.setAuthorId(notesRepository.getLoggedInUserId());
            note.setColourId(selectedColour.getNoteColour().getId());
            notesRepository.addNote(note);
        } else {
            note.setTitle(title);
            note.setDescription(description);
            note.setArchived(archiveCheckBox.isChecked());
            note.setColourId(selectedColour.getNoteColour().getId());
            notesRepository.editNote(note);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();

    }
}