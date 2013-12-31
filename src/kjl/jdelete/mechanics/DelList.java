package kjl.jdelete.mechanics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * stores files as they were in a list. A file's index is its position in the
 * list.
 * 
 * @author zkieda
 */
public class DelList {
    private final DeleteQueue dq;
    public DelList(DeleteQueue dq) {
        assert dq != null;
        this.dq = dq;
    }
    
    /**
     * the files that we want to delete
     */
    private ArrayList<File> files = new ArrayList<>(128);
    
    /**
     * deletes the files at each index given.
     * @requires indices != null && forall i in indices, i>=0 and i<number of 
     *           files in this list
     * 
     * @param indices 
     * @unsafe
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
     * @param index 
     */
    private void fastDel(int index){
        dq.enque(files.get(index));
    }
    
    /**
     * removes each i in indices from our list
     * @param indices 
     */
    public void rem(int[] indices){
        assert indices != null;
        //for the sake of a faster removal.
        for (int i = indices.length-1; i >= 0; i--) {
            rem(indices[i]);
        }
    }
    
    /**
     * removes the file at index from our list
     * @param index 
     */
    public void rem(int index){
        files.remove(index);
    }
    
    /**
     * adds a file to the end of our list. The file's index is one less than the
     * length of the list
     * @param f 
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
        files = new ArrayList<>(128);
    }
    
    
    /**
     * returns the length of the list of files
     */
    public int length(){return files.size();}
    
    /**
     * @param index the index we should return
     * @return the file at the given index
     */
    public File get(int index){
        return files.get(index);
    }
}
