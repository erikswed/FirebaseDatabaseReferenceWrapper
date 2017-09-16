package com.serenegiant.databasereference;

import java.util.UUID;

public abstract class BaseListener {
    public String id;
    public int listId;
    public String description;
    public boolean stopped;
    public String TAG;

    public void createId(){
        this.id = UUID.randomUUID().toString();
    }

    public void setStopped() {
        stopped = true;

    }
}

