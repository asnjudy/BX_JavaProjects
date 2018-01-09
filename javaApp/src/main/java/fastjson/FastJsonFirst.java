package fastjson;

import com.alibaba.fastjson.JSONObject;
import redis.subpub.util.FileUtils;
import redis.subpub.util.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xuwentang on 2018/1/9.
 */
public class FastJsonFirst {

    public static void main(String[] args) throws IOException {

        HashMap<String, Object> map = new HashMap<String, Object>();


        // "C:\\Users\\xuwentang\\Downloads\\zzpic9060\\zzpic9060.JPG"
        map.put("picBytes", FileUtils.getContent("C:\\Users\\xuwentang\\Downloads\\zzpic9060\\zzpic9060.JPG"));

        JSONObject jsonObject = new JSONObject(map);
        byte[] bytes = SerializationUtils.serializeToBytes(jsonObject);
        Object target = SerializationUtils.unserialize(bytes);

        if (target instanceof JSONObject) {
            JSONObject jsonObj = (JSONObject) target;
            // System.out.println(jsonObj);

            // 字节比较
            byte[] picBytesValue = jsonObj.getBytes("picBytes");
            if (picBytesValue == null) {
                return;
            }

            // 保存文件
            FileUtils.saveBytesToFile(picBytesValue, "C:\\Users\\xuwentang\\Downloads\\zzpic9060\\zzpic9060_2.JPG");

        } else {
            System.out.println("error");
        }

    }
}
