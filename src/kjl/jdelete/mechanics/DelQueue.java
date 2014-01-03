package kjl.jdelete.mechanics;

import kjl.jdelete.gui.SimpleLog;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zkieda
 */
public class DelQueue {
//    private Queue<File> del = (Queue<File>)Collections.synchronizedCollection(new ArrayDeque<File>(64));
    private AtomicInteger total = new AtomicInteger();//total number of files deleted
    private int target = 0;//target total number of files to be deleted
    private volatile SimpleLog log;
    public DelQueue(SimpleLog log){
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
                        Process p=Runtime.getRuntime().exec("cmd /c sdelete "+flags+" \""+f.getCanonicalPath()+"\"");
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
    /**
     * @return a float from 0.0f to 1.0f representing the number of files 
     * removed in the current task.
     */
    public float amountComplete(){
        if(target == 0) return 0;
        if(total.get()==target){
            total.set(0);
            return target = 0;
        }
        return (float)total.get()/target;
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
