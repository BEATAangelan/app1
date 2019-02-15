package com.example.dell.myapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bwie.greendao_type1.db.DaoMaster;
import com.bwie.greendao_type1.db.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;

public class Mapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

    }
}
