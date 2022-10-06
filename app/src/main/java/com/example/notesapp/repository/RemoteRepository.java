package com.example.notesapp.repository;

import com.example.notesapp.models.Note;

import java.util.List;

public class RemoteRepository implements NotesRepository {
    @Override
    public void add(Note note) {

    }

    @Override
    public void edit(Note note) {

    }

    @Override
    public Note geyById(long id) {
        return null;
    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
