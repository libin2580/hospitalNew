package com.zulekhahospitals.zulekhaapp.newintro;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Rashid on 10/11/2016.
 */
public class New_HttpHandler {
    private static final String TAG = New_HttpHandler.class.getSimpleName();

    public New_HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        System.out.println("Received URL in  httphandler : "+reqUrl);
        try {
            URL url = new URL(reqUrl);
            System.out.println("Inside try Received URL in  httphandler : "+url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response

            System.out.println("Connection : "+conn);
            InputStream in=new BufferedInputStream(conn.getInputStream());

            System.out.println("Resulting input stream in try :"+in);
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Response in Httphandler : "+response);
        return response;
    }

    private String convertStreamToString(InputStream is) {
        System.out.println("Inside convertstreamtoString ( Received URL in  httphandler ): "+is);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
                System.out.println("appended result inside try in convertStreamToString : "+sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
