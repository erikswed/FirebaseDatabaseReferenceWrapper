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


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import java.util.Map;

interface DatabaseReference {

    public com.google.firebase.database.DatabaseReference child(String s);

    public Task<Void> setValue(com.google.firebase.database.DatabaseReference var1, Object var2, MyOnCompleteListener var3);

    public void setValue(com.google.firebase.database.DatabaseReference var1, Object var2, MyCompletionListener var3);

    public Task<Void> updateChildren(Map<String, Object> map);

    public void updateChildren(Map<String, Object> map, MyCompletionListener var1);

    public Task<Void> removeValue();

    public void removeValue(com.google.firebase.database.DatabaseReference var1, final MyCompletionListener var2);

    public void runTransaction(Transaction.Handler handler);

    public void runTransaction(Transaction.Handler handler, boolean b);

    public void addListenerForSingleValueEvent(Query var1, MyValueEventListener var2);

    public void addListenerForSingleValueEvent(com.google.firebase.database.DatabaseReference var1, MyValueEventListener var2);

}
