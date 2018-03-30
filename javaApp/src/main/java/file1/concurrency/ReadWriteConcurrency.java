package file1.concurrency;

import file1.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class ReadWriteConcurrency {

    final static Logger logger = LoggerFactory.getLogger(ReadWriteConcurrency.class);

    public static void generateFile() {
        logger.info("generate config 1");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("D:\\usr\\files/config1.properties"));

            String line = null;
            int line_count = 1000000;

            String key = "k1_";
            String value = "v1_";

            for (int i = 1; i <= line_count; i++) {
                // k1_1=v1_1
                line = key + i + "=" + value + i + "\n";
                fileOutputStream.write(line.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("generate config 1 finish!!");
    }

    public static void generateFile2() {
        logger.info("generate config 2");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("D:\\usr\\files/config2.properties"));

            String line = null;
            int line_count = 1000000;

            String key = "k2_";
            String value = "v2_";

            for (int i = 1; i <= line_count; i++) {
                line = key + i + "=" + value + i + "\n";
                fileOutputStream.write(line.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("generate config 2 finish!!");
    }


    public static void lockFile() throws InterruptedException {

        final String lockFilePath = "D:\\usr\\files/config1.properties.lock";

        // 启动读线程
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream outStream = null;
                File lockFile = null;
                try {
                    // 先从文件config1.properties.lock上获得锁
                    lockFile = new File(lockFilePath);
                    outStream = new FileOutputStream(lockFile);
                    FileLock lock = outStream.getChannel().lock();
                    // 获得锁后，才能干活
                    FileUtil.printFileContent("D:\\usr\\files/config1.properties");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (lockFile != null && lockFile.exists()) {
                        lockFile.delete();
                    }
                }
            }
        });


        // 启动文件拷贝线程
        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {

                String src = "D:\\usr\\files/config2.properties";
                String target = "D:\\usr\\files/config1.properties";

                //
                FileOutputStream outStream = null;
                File lockFile = null;
                try {
                    // 先从文件config1.properties.lock上获得锁
                    lockFile = new File(lockFilePath);
                    outStream = new FileOutputStream(lockFile);

                    FileLock lock = null;

                    for (int i = 0; i < 1000; i++) {
                        try {
                            lock = outStream.getChannel().tryLock();
                        } catch (Exception ex) {
                            logger.info("Can't get the write lock, {}", ex.toString());
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }
                        if (lock != null) {
                            break;
                        }
                    }

                    // 获得锁后，才能干活
                    logger.info("Copy {} to {}", src, target);
                    FileUtil.copyFile(src, target);
                    logger.info("Copy finish!");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (OverlappingFileLockException e2) {
                    System.out.println("catch .. ");
                    e2.printStackTrace();
                } finally {
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (lockFile != null && lockFile.exists()) {
                        lockFile.delete();
                    }
                }
            }
        });


        readThread.start();
        Thread.sleep(20);
        writeThread.start();


        System.out.println("start");
        readThread.join();
        writeThread.join();
        System.out.println("finish");

    }


    /**
     * 删除文件，delete, 仅当文件没有被打开占用时才能正常删除，返回true
     */
    public static void deleteFile() {
        File file = new File("D:\\usr\\files/config1.properties");

        InputStream configInputStream = null;

        try {
            configInputStream = new FileInputStream("D:\\usr\\files/config1.properties");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (configInputStream != null) {
                try {
                    configInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file.exists()) {
                file.delete();
                logger.warn("delete file: " + file.getAbsolutePath());
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {


        generateFile();
        generateFile2();

        // lockFile();


    }
}
