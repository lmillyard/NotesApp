package com.example.notesapp.repository;

import android.app.Activity;

public class RepositoryProvider {
    public static NotesRepository getInstance(Activity activity) {
        return LocalRepository.getInstance(activity);
    }
}
