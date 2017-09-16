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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is a wrapper around com.google.firebase.database.DatabaseReference. The<br>
 * aim is to centralise and monitor all asynchronous calls to Firebase under one monitor.<br>
 * This class will not in any way keep track of them but instead only eavesdropping on them.<br>
 * <p>
 * To use this class Instead of getting a ref to Firebase with "FirebaseDatabase.getInstance().getReference()"<br>
 * just use "MyDatabaseReference mFirebase = new MyDatabaseReference();"<br>
 * All calls to Firebase are now a bit different like:<br>
 * <p>
 * mFirebase.updateChildren(childUpdates, new MyDatabaseReference.MyCompletionListener("description text)<br>
 * The updateChildren have a custom MyCompletionListener that will be triggered  when <br>
 * Firebase triggers the real CompletionListener<br><br>
 * Basically that´s it and it works the same for  ValueEventListener and ChildEventListener<br>
 */
public class MyDatabaseReference implements DatabaseReference {

    private com.google.firebase.database.DatabaseReference mFirebase;

    private static final LinkedHashMap<Integer, BaseJob> myListeners = new LinkedHashMap<Integer, BaseJob>();

    public MyDatabaseReference() {
        mFirebase = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public com.google.firebase.database.DatabaseReference child(String s) {
        return mFirebase.child(s);
    }

    @Override
    public Task<Void> setValue(com.google.firebase.database.DatabaseReference var1, Object var2, final MyOnCompleteListener var3) {
        com.google.android.gms.tasks.OnCompleteListener cListener =
                new com.google.android.gms.tasks.OnCompleteListener() {
                    @Override
                    public void onComplete(Task task) {
                        if (var3 != null) {
                            var3.onComplete(task);
                            synchronized (myListeners) {
                                myListeners.get(var3.listId).setStopped();
                            }
                        }
                    }
                };
        addWorkItem(var3);
        return var1.setValue(var2, cListener);
    }

    @Override
    public void setValue(com.google.firebase.database.DatabaseReference var1, Object var2, final MyCompletionListener var3) {
        com.google.firebase.database.DatabaseReference.CompletionListener cListener =
                new com.google.firebase.database.DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, com.google.firebase.database.DatabaseReference databaseReference) {
                        if (var3 != null) {
                            var3.onComplete(databaseError, databaseReference);
                            synchronized (myListeners) {
                                myListeners.get(var3.listId).setStopped();
                            }
                        }
                    }
                };
        addWorkItem(var3);
        var1.setValue(var2, cListener);
    }

    @Override
    public Task<Void> updateChildren(Map<String, Object> map) {
        return mFirebase.updateChildren(map);
    }

    @Override
    public void updateChildren(Map<String, Object> map, final MyCompletionListener var1) {
        com.google.firebase.database.DatabaseReference.CompletionListener cListener =
                new com.google.firebase.database.DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, com.google.firebase.database.DatabaseReference databaseReference) {
                        if (var1 != null) {
                            var1.onComplete(databaseError, databaseReference);
                            synchronized (myListeners) {
                                myListeners.get(var1.listId).setStopped();
                            }
                        }
                    }
                };
        addWorkItem(var1);
        mFirebase.updateChildren(map, cListener);
    }

    @Override
    public Task<Void> removeValue() {
        return mFirebase.removeValue();
    }

    @Override
    public void removeValue(com.google.firebase.database.DatabaseReference ref, final MyCompletionListener var1) {
        com.google.firebase.database.DatabaseReference.CompletionListener cListener =
                new com.google.firebase.database.DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, com.google.firebase.database.DatabaseReference databaseReference) {
                        if (var1 != null) {
                            var1.onComplete(databaseError, databaseReference);
                            synchronized (myListeners) {
                                myListeners.get(var1.listId).setStopped();
                            }
                        }
                    }
                };
        ref.removeValue(cListener);
        addWorkItem(var1);
    }

    @Override
    public void runTransaction(Transaction.Handler handler) {
        // TODO not in use and not configured with a custom handler
        mFirebase.runTransaction(handler);
    }

    @Override
    public void runTransaction(Transaction.Handler handler, boolean b) {
        // TODO not in use and not configured with a custom handler
        mFirebase.runTransaction(handler, b);
    }

    @Override
    public void addListenerForSingleValueEvent(com.google.firebase.database.DatabaseReference ref
            , final MyValueEventListener var1) {
        com.google.firebase.database.ValueEventListener vListener =
                new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (var1 != null) {
                            var1.onDataChange(dataSnapshot);
                            synchronized (myListeners) {
                                myListeners.get(var1.listId).setStopped();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (var1 != null) {
                            var1.onCancelled(databaseError);
                            synchronized (myListeners) {
                                myListeners.get(var1.listId).setStopped();
                            }
                        }
                    }
                };
        ref.addListenerForSingleValueEvent(vListener);
        addWorkItem(var1);
    }

    @Override
    public void addListenerForSingleValueEvent(com.google.firebase.database.Query q
            , final MyValueEventListener var1) {
        com.google.firebase.database.ValueEventListener vListener =
                new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (var1 != null) {
                            var1.onDataChange(dataSnapshot);
                            synchronized (myListeners) {
                                myListeners.get(var1.listId).setStopped();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (var1 != null) {
                            var1.onCancelled(databaseError);
                            synchronized (myListeners) {
                                myListeners.get(var1.listId).setStopped();
                            }
                        }
                    }
                };

        q.addListenerForSingleValueEvent(vListener);
        addWorkItem(var1);
    }

    public com.google.firebase.database.ValueEventListener addMyValueEventListener(final Query query, final MyValueEventListener var1) {
        com.google.firebase.database.ValueEventListener Vlistener = new com.google.firebase.database.ValueEventListener() {
            boolean alreadyStopped;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (var1 != null) {
                    var1.onDataChange(dataSnapshot);
                    if (!alreadyStopped)
                        // Since the WorkItem only need to be stopped once and also this gets
                        // triggered once for each existing child no need to stop it that many times
                        synchronized (myListeners) {
                            myListeners.get(var1.listId).setStopped();
                            alreadyStopped = true;
                        }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (var1 != null) {
                    var1.onCancelled(databaseError);
                    synchronized (myListeners) {
                        myListeners.get(var1.listId).setStopped();
                        alreadyStopped = true;
                    }
                }
            }
        };
        addWorkItem(var1);
        return query.addValueEventListener(Vlistener);
    }

    public com.google.firebase.database.ChildEventListener addMyChildEventListener
            (final Query query, final MyChildEventListener var1) {
        com.google.firebase.database.ChildEventListener clistener =
                new com.google.firebase.database.ChildEventListener() {
                    boolean alreadyStopped;

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (var1 != null) {
                            var1.onChildAdded(dataSnapshot, s);
                            if (!alreadyStopped)
                                // Since the WorkItem only need to be stopped once and also this gets
                                // triggered once for each existing child no need to stop it that many times
                                synchronized (myListeners) {
                                    myListeners.get(var1.listId).setStopped();
                                    alreadyStopped = true;
                                }
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        if (var1 != null)
                            var1.onChildChanged(dataSnapshot, s);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        if (var1 != null)
                            var1.onChildRemoved(dataSnapshot);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        if (var1 != null)
                            var1.onChildMoved(dataSnapshot, s);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        var1.onCancelled(databaseError);
                    }
                };
        addWorkItem(var1);
        return query.addChildEventListener(clistener);
    }

    private void addWorkItem(BaseListener listener) {
        synchronized (myListeners) {
            listener.listId = myListeners.size();
            listener.createId();
            String d = listener.getClass().getName();
            myListeners.put(listener.listId, new WorkItem(listener.id, listener.description, listener.listId, listener.TAG));
        }
    }
}