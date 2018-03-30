package file1.concurrency;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class WriteThread {

    static String lockFilePath = null;

    static {
        try {
            String classpath = WriteThread.class.getClassLoader().getResource("").toURI().getPath();
            lockFilePath = classpath + "conf.properties.lock";
            System.out.println(lockFilePath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void start() {
        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {

                FileOutputStream outStream = null;
                File lockFile = null;
                FileLock lock = null;
                try {
                    // 先从文件config1.properties.lock上获得锁
                    lockFile = new File(lockFilePath);
                    outStream = new FileOutputStream(lockFile);

                    while (true) {

                        try {
                            lock = outStream.getChannel().tryLock();
                            if (lock == null) {
                                System.out.println("Writer can't get the lock on " + lockFilePath + "!! try again!");
                                Thread.sleep(2000);
                                continue;
                            }
                            if (lock != null) {
                                System.out.println("Writer get the lock on " + lockFilePath + ", OK");
                                break;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("Writer can't get the lock!! try again!");
                            Thread.sleep(2000);
                        }
                    }

                    while (true) {
                        Thread.sleep(2000);
                        System.out.println("write ..");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (OverlappingFileLockException e2) {
                    System.out.println("catch .. ");
                    e2.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock == null) {
                        System.out.println("Can't get the write lock, ");
                    }
                    if (lock != null) {
                        try {
                            lock.release(); // 释放锁
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (outStream != null) {
                        try {
                            outStream.close(); // 关闭打开的文件句柄
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        System.out.println("write thread start");
        writeThread.start();
    }

    public static void main(String[] args) throws InterruptedException {
        new WriteThread().start();
        System.out.println("write thread end");
    }
}
