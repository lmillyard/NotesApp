package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notesapp.models.DaoSession;
import com.example.notesapp.models.Note;
import com.example.notesapp.models.NoteDao;
import com.example.notesapp.models.NotesAdapter;
import com.example.notesapp.repository.NotesRepository;
import com.example.notesapp.repository.RepositoryProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.addNoteButton).setOnClickListener(v -> openAddScreen());
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotesRepository notesRepository = RepositoryProvider.getInstance(this);
        List<Note> notes = notesRepository.getAllNotes();

        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        NotesAdapter notesAdapter = new NotesAdapter(this, notes);
        notesRecyclerView.setAdapter(notesAdapter);

    }

    private void openAddScreen() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);

    }
}