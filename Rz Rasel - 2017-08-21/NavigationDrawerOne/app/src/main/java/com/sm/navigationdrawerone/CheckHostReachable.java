package com.sm.navigationdrawerone;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/**
 * Created by Rz Rasel on 2017-04-19.
 */

public class CheckHostReachable {
    //class RetrieveData extends AsyncTask<String, Void, String> {
    class RetrieveData extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestProperty("connection", "close");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(100000);
                httpURLConnection.setRequestMethod("HEAD");
                System.out.println("HTTP_URL: " + params[0]);
                System.out.println(httpURLConnection.getResponseCode());
                if(httpURLConnection!=null) httpURLConnection.disconnect();
                return (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
            } catch (Exception e) {
                //e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                System.out.println("Exists");
            else
                System.out.println("Not Exists");
        }
    }

    public boolean onCheckByHttpURLConnection(final String argHostUrl) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                // Do network action in this function
                try {
                    HttpURLConnection.setFollowRedirects(false);
                    HttpURLConnection con = (HttpURLConnection) new URL(argHostUrl).openConnection();
                    con.setRequestMethod("HEAD");
                    System.out.println(con.getResponseCode());
                    System.out.println("HOST_URL_ERROR_CATCH " + HttpURLConnection.HTTP_OK);
                    //return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    //return false;
                }
            }
        }).start();*/
        RetrieveData task = new RetrieveData();
        task.execute(argHostUrl);
        return false;
    }

    public boolean onGetByName(final String argHostUrl) {
        /*try {
            InetAddress.getByName(argHostUrl).isReachable(2000); //Replace with your name
            System.out.println("HOST_URL_OK");
            return true;
        } catch (Exception e) {
            System.out.println("HOST_URL_ERROR_CATCH");
            e.printStackTrace();
            return false;
        }*/
        //System.out.println("HOST_URL_ERROR_CATCH");
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress.getByName(argHostUrl).isReachable(2000); //Replace with your name
                    System.out.println("HOST_URL_OK");
                    //return true;
                } catch (Exception e) {
                    System.out.println("HOST_URL_ERROR_CATCH");
                    e.printStackTrace();
                    //return false;
                }
            }
        };
        new Thread(runnable).start();*/
        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    InetAddress.getByName(argHostUrl).isReachable(2000); //Replace with your name
                    System.out.println("HOST_URL_OK");
                    //return true;
                } catch (Exception e) {
                    System.out.println("HOST_URL_ERROR_CATCH");
                    e.printStackTrace();
                    //return false;
                }
            }
        };

        thread.start();*/
        /*final Runnable r = new Runnable() {
            public void run() {
                tv.append("Hello World");
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);*/
        return false;
    }

    public boolean onCheckBySocket(final String argHostUrl) {
        final Socket socket;

        try {
            URL url = new URL(argHostUrl);
            socket = new Socket(url.getHost(), url.getPort());
            System.out.println("Exists");
        } catch (IOException e) {
            System.out.println("Not Exists");
            return false;
        }

        try {
            socket.close();
        } catch (IOException e) {
            // will never happen, it's thread-safe
        }
        System.out.println("Not Exists");
        return true;
    }
}
/*
http://stackoverflow.com/questions/26418486/check-if-url-exists-or-not-on-server
*/