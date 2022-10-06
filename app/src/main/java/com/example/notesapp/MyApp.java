package com.example.notesapp;

import android.app.Application;
import com.example.notesapp.models.DaoMaster;
import com.example.notesapp.models.DaoSession;
import org.greenrobot.greendao.database.Database;

public class MyApp extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // regular SQLite database
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
