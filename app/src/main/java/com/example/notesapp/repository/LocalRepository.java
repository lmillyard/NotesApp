package com.example.notesapp.repository;

import android.app.Activity;
import com.example.notesapp.MyApp;
import com.example.notesapp.callbacks.LoginCallback;
import com.example.notesapp.callbacks.SignUpCallback;
import com.example.notesapp.models.Author;
import com.example.notesapp.models.AuthorDao;
import com.example.notesapp.models.DaoSession;
import com.example.notesapp.models.Note;
import com.example.notesapp.models.NoteColour;
import com.example.notesapp.models.NoteColourDao;
import com.example.notesapp.models.NoteDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class LocalRepository implements NotesRepository {
    private DaoSession daoSession;
    private NoteDao noteDao;
    private AuthorDao authorDao;
    private NoteColourDao noteColourDao;
    private static LocalRepository instance;
    private Author loggedInUser;

    private LocalRepository(Activity activity) {
        daoSession = ((MyApp)activity.getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();
        authorDao = daoSession.getAuthorDao();
        noteColourDao = daoSession.getNoteColourDao();
        seedColoursIfNeeded();

    }

    public static LocalRepository getInstance(Activity activity) {
        if (instance == null) {
            instance = new LocalRepository(activity);
        }
        return instance;
    }

    private void seedColoursIfNeeded() {
        List<NoteColour> noteColours = getNoteColours();
        if (noteColours == null || noteColours.isEmpty()) {
               NoteColour noteColour1 = new NoteColour();
               noteColour1.setColourCode("#FF00BCD4");
            NoteColour noteColour2 = new NoteColour();
            noteColour2.setColourCode("#FFFFC107");
            NoteColour noteColour3 = new NoteColour();
            noteColour3.setColourCode("#FF9C27B0");

            noteColourDao.insert(noteColour1);
            noteColourDao.insert(noteColour2);
            noteColourDao.insert(noteColour3);

        }
    }

    @Override
    public List<NoteColour> getNoteColours() {
        return noteColourDao.getSession().loadAll(NoteColour.class);
    }

    public Author getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void addNote(Note note) {
        noteDao.insert(note);
        loggedInUser.getNotes().add(note);
    }

    @Override
    public void editNote(Note note) {
        noteDao.update(note);
    }

    @Override
    public Note getNoteById(long id) {
        return noteDao.getSession().load(Note.class, id);
    }

    @Override
    public List<Note> getAllNotes() {
        return loggedInUser.getNotes();
    }

    @Override
    public void deleteNote(long id) {
        Note note = getNoteById(id);
        noteDao.delete(note);

    }

    @Override
    public long getLoggedInUserId() {
        return loggedInUser.getId();
    }

    @Override
    public void signUp(String username, String password, SignUpCallback signUpCallback) {

        List<Author> authors = authorDao.getSession().queryBuilder(Author.class)
                .where(AuthorDao.Properties.Name.eq(username)).build().list();
        if (authors.size() == 0) {
            Author author = new Author();
            author.setName(username);
            author.setPassword(password);
            signUpCallback.onSuccess();
        } else {
            signUpCallback.onFailure("Username already in use");
        }

    }

    @Override
    public void logIn(String name, String password, LoginCallback loginCallback) {
        List<Author> authors = authorDao.getSession().queryBuilder(Author.class)
                .where(AuthorDao.Properties.Name.eq(name)).build().list();

        if (authors.size() == 0) {
            loginCallback.onFailure("Username not found.");
        } else {
            Author author = authors.get(0);
            loggedInUser = author;
            if (author.getPassword().equals(password)) {
                loginCallback.onSuccess();
            } else {
                loginCallback.onFailure("Password incorrect.");
            }
        }
    }
}
