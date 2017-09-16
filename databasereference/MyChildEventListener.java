package com.serenegiant.databasereference;

import com.google.firebase.database.ChildEventListener;

public abstract class MyChildEventListener extends BaseListener implements ChildEventListener {

    public MyChildEventListener(String description) {
        this.description = description;
        TAG = MyChildEventListener.class.getSimpleName();
    }
}
