package sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class ClientFormLogin {


    public static void testHttpGet() throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://localhost:8080/api/web/config/versionlist");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        System.out.println("Login form get: " + response.getStatusLine());


        if (entity != null) {
            entity.consumeContent();
        }
        System.out.println("Initial set of cookies:");
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }
    }

    public static void testHttpPost() throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        String uri = "http://localhost:8080/api/account/signin";

        System.out.println("uri: " + uri);

        HttpPost httpost = new HttpPost(uri);

        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("name", "admin"));
        nvps.add(new BasicNameValuePair("password", "admin"));
        nvps.add(new BasicNameValuePair("remember", "0"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        HttpResponse response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            readResponse(inputStream);
        }

        System.out.println("Post logon cookies:");
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("cookie: " + cookies.get(i).getName() + "=" + cookies.get(i).getValue());
            }
        }
    }

    public static void readResponse(InputStream inputStream) throws IOException {
        StringBuilder resultContent = new StringBuilder();

        byte [] buffer = new byte[1024];
        int n = 0;
        while ((n = inputStream.read(buffer)) != -1) {
            resultContent.append(new String(buffer, 0, n));
        }
        System.out.println("####content#### " + resultContent);
    }




    public static void main(String[] args) throws Exception {

        testHttpGet();
    }



}
