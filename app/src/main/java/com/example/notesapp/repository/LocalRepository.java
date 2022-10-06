package com.example.notesapp.repository;

import android.app.Activity;
import com.example.notesapp.MyApp;
import com.example.notesapp.models.DaoSession;
import com.example.notesapp.models.Note;
import com.example.notesapp.models.NoteDao;

import java.util.List;

public class LocalRepository implements NotesRepository {
    private DaoSession daoSession;
    private NoteDao noteDao;
    private static LocalRepository instance;

    private LocalRepository(Activity activity) {
        daoSession = ((MyApp)activity.getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();
    }

    public static LocalRepository getInstance(Activity activity) {
        if (instance == null) {
            instance = new LocalRepository(activity);
        }
        return instance;
    }
    
    @Override
    public void add(Note note) {
        noteDao.insert(note);
    }

    @Override
    public void edit(Note note) {
        noteDao.update(note);
    }

    @Override
    public Note geyById(long id) {
        return noteDao.getSession().load(Note.class, id);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDao.getSession().loadAll(Note.class);
    }

    @Override
    public void delete(long id) {

    }
}
