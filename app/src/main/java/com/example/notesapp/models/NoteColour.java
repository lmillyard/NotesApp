package com.example.notesapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NoteColour {
    @Id(autoincrement = true)
    private long id;

    private String colourCode; //#00ff00ff

    @Generated(hash = 660706388)
    public NoteColour(long id, String colourCode) {
        this.id = id;
        this.colourCode = colourCode;
    }

    @Generated(hash = 773462039)
    public NoteColour() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColourCode() {
        return this.colourCode;
    }

    public void setColourCode(String colourCode) {
        this.colourCode = colourCode;
    }
}
