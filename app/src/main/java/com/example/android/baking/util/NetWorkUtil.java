package com.example.android.baking.util;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by milkdz on 2018/2/22.
 */

public class NetWorkUtil {

    private static String TAG = "NetWorkUtil";

    /**
     * Build url with http string address
     *
     * @param sHttpAdd address
     * @return url
     * @throws MalformedURLException
     */
    @org.jetbrains.annotations.Contract("null -> null")
    public static URL buildUrlFromHttp(String sHttpAdd) throws MalformedURLException {
        if(sHttpAdd == null){
            return null;
        }
        Uri uri = Uri.parse(sHttpAdd).buildUpon().build();
        URL url = new URL(uri.toString());
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d(TAG,"url -- > " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //urlConnection.setConnectTimeout(10000);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
