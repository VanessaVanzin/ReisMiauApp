package com.example.reismiauapp;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class ReisMiauApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
