package com.nishat.vconfess;

import android.app.Application;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nishat on 9/24/2017.
 */

public class VConfess extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
