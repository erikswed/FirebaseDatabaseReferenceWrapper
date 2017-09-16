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
