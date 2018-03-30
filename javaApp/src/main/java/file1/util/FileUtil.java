package file1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class FileUtil {

    static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    public static void printFileContent(String filePath) {

        BufferedReader bufferedReader = null;
        try {

            InputStream configInputStream = new FileInputStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(configInputStream));

            String line = null;

            int count = 1;
            while ((line = bufferedReader.readLine()) != null) {
                logger.info(count + ": " + line);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("read_finish!!");
    }


    /**
     * 读取文件 src 的内容，拷贝到target中
     * @param src
     * @param target
     */
    public static void copyFile(String src, String target) {

        BufferedReader bufferedReader = null;
        FileOutputStream fileOutputStream = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(src)));

            fileOutputStream = new FileOutputStream(new File(target));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                // 把读出的一行，写到 target文件中
                fileOutputStream.write((line + "\n").getBytes());
                logger.info("Write {}", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
