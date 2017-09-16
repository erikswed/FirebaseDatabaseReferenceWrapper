package com.serenegiant.databasereference;

public abstract class MyCompletionListener extends  BaseListener implements DatabaseReference.CompletionListener {

    public MyCompletionListener(String description) {
        this.description = description;
        TAG = MyCompletionListener.class.getSimpleName();
    }
}
