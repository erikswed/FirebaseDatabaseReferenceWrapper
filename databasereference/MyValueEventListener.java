package com.serenegiant.databasereference;

import com.google.firebase.database.ValueEventListener;

public abstract class MyValueEventListener extends BaseListener implements ValueEventListener {

    public MyValueEventListener(String description) {
        this.description = description;
        TAG = MyValueEventListener.class.getSimpleName();
    }
}
