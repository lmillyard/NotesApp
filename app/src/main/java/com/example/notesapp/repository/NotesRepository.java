package com.example.notesapp.repository;
import com.example.notesapp.models.Note;

import java.util.List;

public interface NotesRepository {
    void add(Note note);
    void edit(Note note);
    Note geyById(long id);
    List<Note> getAllNotes();
    void delete(long id);
}
