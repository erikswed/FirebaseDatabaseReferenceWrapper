package com.serenegiant.databasereference;

import com.port.android.data.Application;
import com.port.android.data.event.OnFirebaseEvent;

public abstract class BaseJob{

    public String id;
    public int listId;
    public String description;
    public boolean stopped;
    public String TAG;

    public void setStopped() {
        stopped = true;
        Application.getInstance().getEventBus().post(new OnFirebaseEvent(this));
    }
}
