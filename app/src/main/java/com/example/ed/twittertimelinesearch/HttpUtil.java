package com.example.ed.twittertimelinesearch;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HttpUtil {
    public String getHttpResponse(HttpRequestBase request) {
        String result = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpResponse httpResponse = httpClient.execute(request);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String reason = httpResponse.getStatusLine().getReasonPhrase();
            StringBuilder sb = new StringBuilder();
            if (statusCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                Log.e("errT", String.valueOf(entity));
                InputStream inputStream = entity.getContent();
                BufferedReader bReader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"), 8);
                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                }
            } else if (statusCode == 404) {
                //do something if user not found error is true
                Log.e("errT <404>", String.valueOf(statusCode));
            } else {
                sb.append(reason);
            }
            result = sb.toString();
        } catch (UnsupportedEncodingException ex) {
        } catch (ClientProtocolException ex1) {
        } catch (IOException ex2) {
        }
        return result;
    }
}
