package com.example.notesapp.repository;
import com.example.notesapp.callbacks.LoginCallback;
import com.example.notesapp.models.Author;
import com.example.notesapp.models.Note;

import java.util.List;

public interface NotesRepository {
    void addNote(Note note);
    void editNote(Note note);
    Note getNoteById(long id);
    List<Note> getAllNotes();
    void deleteNote(long id);

    void signUp(Author author);
    void logIn(String name, String password, LoginCallback loginCallback);
}
