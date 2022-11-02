package com.example.notesapp.viewmodels;

import com.example.notesapp.models.NoteColour;

public class NoteColourViewModel {

    private NoteColour noteColour;
    private boolean selected;

    public NoteColourViewModel(NoteColour noteColour) {
        this.noteColour = noteColour;
    }

    public NoteColour getNoteColour() {
        return noteColour;
    }

    public void setNoteColour(NoteColour noteColour)  {
        this.noteColour = noteColour;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
