/*
 * Copyright (C) 2016 CWDO Systems Ltd  All Rights Reserved.
 * Unauthorized copying in any way of this file via any medium
 * is strictly prohibited Proprietary and confidential.
 *  <portplayers@gmail.com>, October 2016
 *
 * FURTHERMORE CWDO CONFIDENTIAL
 * __________________
 *
 * [2015] - [2030] CWDO Systems Ltd
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of CWDO Systems Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to CWDO Systems Ltd
 * and its suppliers and may be covered by patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from CWDO Systems Ltd.
 */

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
