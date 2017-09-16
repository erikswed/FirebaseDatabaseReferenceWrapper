package com.serenegiant.databasereference;

import com.port.android.data.Application;
import com.port.android.data.event.OnFirebaseEvent;

public class WorkItem extends BaseJob {

    public WorkItem(String id, String description, int listId, String TAG) {
        this.id = id;
        this.description = description;
        this.listId = listId;
        this.TAG = TAG;

    }

    public void fireEvent(){
        Application.getInstance().getEventBus().post(new OnFirebaseEvent(this));
    }
}
