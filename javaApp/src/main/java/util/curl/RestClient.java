package util.curl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by xuwentang on 2017/10/15.
 */
public class RestClient {

    public static String postJson(String uri, String data) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(uri);

        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(data));

        HttpResponse httpResponse = httpClient.execute(httpPost);
        String response = EntityUtils.toString(httpResponse.getEntity());

        return response;
    }

    public static void main(String[] args) throws IOException {



        for (int k=0; k < 100; k++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; ; i++) {
                        try {
                            String data = "{ \"username\": \"admin\", \"tenant\": \"root\", \"password\": \"admin\"}";
                            long start = System.currentTimeMillis();
                            String res0 = null;
                            res0 = RestClient.postJson("http://127.0.0.1:8080/aas/api/session", data);
                            long timeUsed = System.currentTimeMillis() - start;
                            System.out.println("[" + i + "] " + "[" + timeUsed + "ms]: " + res0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }).start();
        }




    }
}
