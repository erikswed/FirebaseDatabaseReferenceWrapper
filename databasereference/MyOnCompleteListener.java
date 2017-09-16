package com.serenegiant.databasereference;

import com.google.android.gms.tasks.OnCompleteListener;

public abstract class MyOnCompleteListener extends BaseListener implements OnCompleteListener {

    public MyOnCompleteListener(String description) {
        this.description = description;
        TAG = MyOnCompleteListener.class.getSimpleName();
    }
}
