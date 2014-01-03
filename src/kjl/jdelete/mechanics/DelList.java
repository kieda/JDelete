package kjl.jdelete.mechanics;

import java.io.File;
import java.util.ArrayList;

/**
 * stores files as they were in a list. A file's index is its position in the
 * list. Essentially behaves like a recycling bin - place things here to decide
 * whether or not it should be permanently deleted, then delete them permanently
 * from here.
 * 
 * @author zkieda
 */
public class DelList {
    //we will add files to this delete queue when they need to be deleted
    private final DelQueue dq;
    
    //the minimum recycle bin size that we will have (for the sake of enqueing
    //many elements)
    private static final int DEFAULT_BUF_SIZE = 128;
    /**
     * Instantiates a DelList that uses some implementation of a DelQueue to 
     * permanently delete files listed on this DelList. 
     * @param dq the DelQueue
     */
    public DelList(DelQueue dq) {
        assert dq != null;
        this.dq = dq;
    }
    
    /**
     * the files that we want to delete
     */
    private ArrayList<File> files = new ArrayList<>(DEFAULT_BUF_SIZE);
    
    /**
     * deletes the files at each index given.
     * @requires indices != null && forall i in indices, i>=0 and i<number of 
     *           files in this list and indices is sorted. 
     * 
     * @param indices We remove all files at the given indices in our recycle 
     * bin, and queue them for deletion
     * @unsafe (don't try to access with multiple threads.)
     */
    public void del(int[] indices){
        assert indices != null;
        for (int i = 0; i < indices.length; i++) {
            fastDel(indices[i]);
        }
        rem(indices);
    }
    /**
     * deletes the file at the given index, and removes it from our list
     */
    public void del(int index){
        fastDel(index);
        rem(index);
    }
    /**
     * quickly deletes the file. Does not remove it from our list
     * @param index the index in the recycle bin to permanently delete
     */
    private void fastDel(int index){
        dq.enque(files.get(index));
    }
    
    /**
     * removes each i in indices from our list
     * @param indices We remove all files at the given indices in our recycle 
     * bin
     */
    public void rem(int[] indices){
        assert indices != null;
        //assert indices is sorted from least to greatest;
        
        for (int i = indices.length-1; i >= 0; i--) {
            rem(indices[i]);
        }
    }
    
    /**
     * removes the file at index from our list
     * @param index The index to remove
     */
    public void rem(int index){
        files.remove(index);
    }
    
    /**
     * adds a file to the end of our list. The file's index is one less than the
     * length of the list
     * @param f The file to add to our recycling bin
     */
    public void add(File f){
        files.add(f);
    }
    /**
     * deletes all of the elements in the list
     */
    public void delAll(){
        for(int i = 0; i < files.size(); i++){
            fastDel(i);
        }
        files.clear();
    }
    
    
    /**
     * returns the length of the list of files
     */
    public int length(){return files.size();}
    
    /**
     * Returns the file at a given index in the recycle bin.
     * @param index the index we should return
     * @return the file at the given index
     */
    public File get(int index){
        return files.get(index);
    }
}
