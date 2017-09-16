# FirebaseDatabaseReferenceWrapper
This is a experimental wrapper around com.google.firebase.database.DatabaseReference.

The aim is to centralise and monitor all asynchronous calls to Firebase under one monitor.

This class will not in any way keep track of them but instead only eavesdropping on them.

To use this class Instead of getting a ref to Firebase with "FirebaseDatabase.getInstance().getReference()"
just use "MyDatabaseReference mFirebase = new MyDatabaseReference();"
All calls to Firebase are now a bit different like:
 mFirebase.updateChildren(childUpdates, new MyDatabaseReference.MyCompletionListener("description text)
The updateChildren have a custom MyCompletionListener that will be triggered  when 
Firebase triggers the real CompletionListener.
  
There´s more to do since this initial version is not handling all cases.

See the DebugViewer.java for an example usage!

Have fun!
