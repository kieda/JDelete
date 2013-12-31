package kjl.jdelete.mechanics;

import kjl.jdelete.gui.SimpleLog;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zkieda
 */
public class DeleteQueue {
//    private Queue<File> del = (Queue<File>)Collections.synchronizedCollection(new ArrayDeque<File>(64));
    private AtomicInteger total = new AtomicInteger();//total number of files deleted
    private int target = 0;//target total number of files to be deleted
    private volatile SimpleLog log;
    public DeleteQueue(SimpleLog log){
        this.log = log;
    }
    private volatile String flags = "";
    public void setFlags(String flags) {
        this.flags = flags;
    }
    
    public void enque(final File f){
        target++;
        e.submit(new Runnable() {
            @Override
            public void run() {
                if(!f.exists()){
                    log.append(        "Error: file doesn't exist : "+f+"\n");
                } else {
                    try {
//                        System.out.println("cmd /c sdelete "+flags+" \""+f.getCanonicalPath()+"\"");
                        Process p=Runtime.getRuntime().exec(//"cmd /c dir");
                             "cmd /c sdelete "+flags+" \""+f.getCanonicalPath()+"\"");
                        if(p.waitFor() != 0){
                            log.append("Error: deleting file      : "+f+"\n");
                        }
                    } catch (Exception e) {
                        log.append(    "Error: making process for : "+f+"\n");
                    }
                }
                total.incrementAndGet();
            }
        });
    }
    public float percentComplete(){
        if(target == 0) return 0;
        if(total.get()==target){
            total.set(0);
            return target = 0;
        }
        return (float)(100*total.get())/target;
    }
    private ExecutorService e = Executors.newSingleThreadExecutor();
        //we don't know if sdelete fucks up on multiple threads. Let's play it 
        //safe.
    
//    public static void main(String[] args) {
//        DeleteQueue dq = new DeleteQueue();
//        for(int i=0;i<100;i++)
//            dq.enque(new File(""+i));
//    }
    public void close(){e.shutdown();}
}
