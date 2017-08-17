package com.example.krishna.online_shopping;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by AbhijitNag on 4/30/2016.
 */
public class JSONParser {
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public JSONObject getJsonFromURL(String getURL) {
        InputStream inputStream = null;
        JSONObject jsonObject = null;
        try {
            urlObj = new URL(getURL);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//             // requesting post method
//httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.connect();
//           int responseCode = httpURLConnection.getResponseCode(); // getting response code
//
//            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());


            // Creating an http connection to communicate with url
            conn = (HttpURLConnection) urlObj.openConnection();

            // Connecting to url
            conn.connect();

            // Reading data from url
            inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            result = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                result.append(line).append("\n");
            }

            //result = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null)
            {
                conn.disconnect();
            }
        }

        try {
            jsonObject = new JSONObject(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //insert data

    public JSONObject insertJsonFromUrl(String url, String method,
                                        HashMap<String, String> params) {

//    	sbParams = new StringBuilder();
//        boolean first = true;
//        	try
//        	{
//		        for (NameValuePair pair : params)
//		        {
//		            if (first)
//		                first = false;
//		            else
//		            	sbParams.append("&");
//
//		            sbParams.append(URLEncoder.encode(pair.getName(), "UTF-8"));
//		            sbParams.append("=");
//		            sbParams.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
//		        }
//        	}
//        	catch(Exception e)
//        	{
//        		 e.printStackTrace();
//        	}

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }


        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
}

