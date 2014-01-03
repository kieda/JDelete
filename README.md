JDelete
=======

Gui wrapper for SDelete, a utility to safely delete files. pre-beta. Used this to scrub the files off of my old laptop before selling it. So I guess it works?

=======

notice : This library was written for windows, due to the nature of the sdelete function

In order to run this properly, you must put sdelete (version 1.51) in the main directory. So download it and put it there yourself ya dingus.

This program also uses the "FileDrop" library, which is located in "src/org/filedrop/FileDrop.java". See : http://iharder.sourceforge.net/current/java/filedrop/

Then, simply drag 'n drop a files from Explorer into this program to queue up the recycle bin. Select the ones you want to delete then press "delete" to delete those files permanently, or delete all of the files on the list by pressing "Delete All". Remove items from the current list by the "remove" button. Removed items are not deleted.
