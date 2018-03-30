package file1.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Test {

    static final Logger log = LoggerFactory.getLogger(Test.class);


    /**
     * @param file
     * @param strToWrite
     * @param append
     * @param lockTime  以毫秒为单位,该值只是方便模拟排他锁时使用，－1表示不考虑该字段
     * @return
     */
    public static boolean lockAndWrite(File file, String strToWrite, boolean append, int lockTime){
        if(!file.exists()){
            return false;
        }
        RandomAccessFile fis = null;
        FileChannel fileChannel = null;
        FileLock fl = null;
        long tsBegin = System.currentTimeMillis();
        try {
            fis = new RandomAccessFile(file, "rw");
            fileChannel = fis.getChannel();
            fl = fileChannel.tryLock();
            if(fl == null || !fl.isValid()){
                return false;
            }
            log.info("threadId = {} lock success", Thread.currentThread());
            // if append
            if(append){
                long length = fis.length();
                fis.seek(length);
                fis.writeUTF(strToWrite);
                //if not, clear the content , then write
            }else{
                fis.setLength(0);
                fis.writeUTF(strToWrite);
            }
            long tsEnd = System.currentTimeMillis();
            long totalCost = (tsEnd - tsBegin);
            if(totalCost < lockTime){
                Thread.sleep(lockTime - totalCost);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            if(fl != null){
                try {
                    fl.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileChannel != null){
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    public static void main(String[] args) throws InterruptedException {

        Thread th1 = new Thread("write-thread-1-lock"){
            @Override
            public void run() {
                lockAndWrite(new File("d:/usr/data/hello.txt"), "write-thread-1-lock" + System.currentTimeMillis(), false, 30 * 1000);}

        };

        Thread th2 = new Thread("write-thread-2-lock"){
            @Override
            public void run() {
                lockAndWrite(new File("d:/usr/data/hello.txt"), "write-thread-2-lock" +  System.currentTimeMillis(), false, 30 * 1000);
            }

        };

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println("finish");
    }

}
