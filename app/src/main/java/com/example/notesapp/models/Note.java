package com.example.notesapp.models;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity()
public class Note {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String title;
    private String description;
    @Generated(hash = 440087190)
    public Note(Long id, @NotNull String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
