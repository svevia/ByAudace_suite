package com.frenchcomputerguy.rest;

import android.util.Log;

import com.frenchcomputerguy.utils.JSONElement;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an HTTP request.
 *
 * @author Quentin Brault
 * @since 2016/03/16
 */
public class Request {

    protected static final int GET = 0;

    protected static final int POST = 1;

    protected static final int PUT = 2;

    protected static final int DELETE = 3;

    //private HttpClient httpclient = new DefaultHttpClient();
    private HttpURLConnection connection = null;
    private String url;
    //private HttpUriRequest request;

    // private String url;

    private Map<String, String> parameters;

    public Request(String url) {
        this.url = url;
    }

    public Request(String url, Map<String, String> parameters) {
        this.url = url;
        this.parameters = parameters;
    }

    /**
     * Sends an HTTP request and returns the response parsed in JSON, or null if there was a problem.
     * @param method Request method to send.
     * @return Response body parsed to json, or null if there was a problem.
     */
    protected JSONElement fetch(final int method) {
       /* try {
            urlServer = new URL(url);
            connection = (HttpURLConnection) urlServer.openConnection();
            String response = new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String[] params) {
                    try {
                        switch (method) {
                            case GET:
                                connection.setRequestMethod("GET");
                                break;
                            case POST:
                                connection.setRequestMethod("POST");
                                break;
                            case PUT:
                                connection.setRequestMethod("PUT");
                                break;
                            case DELETE:
                                connection.setRequestMethod("DELETE");
                                break;
                            default:
                                connection.setRequestMethod("GET");
                        }
                    } catch (ProtocolException e){
                        e.printStackTrace();
                    }
                    String line = null;
                    try {
                        InputStream content = connection.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(content));
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    /*for (String parameter : parameters.keySet()) {
                        request.addHeader(parameter, parameters.get(parameter));
                    }
                    ResponseHandler<String> handler = new BasicResponseHandler();
                    String result = null;
                    try {
                        result = httpclient.execute(request, handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    httpclient.getConnectionManager().shutdown();
                    return line;
                }
            }.execute(url).get();

            if (response == null)
                return null;
            try {
                return new JSONElement(response);
            } catch (JSONException e) {
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }*/
        return null;
    }

    public JSONElement GET(final String url, Map<String, String> parameters) {
       /* try {

            String response = new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String[] params) {
                    HttpURLConnection client = null;
                    URL url1 = null;
                    try {
                        url1 = new URL(url);
                        client = (HttpURLConnection) url1.openConnection();
                        client.setRequestMethod("GET");
                    } catch (MalformedURLException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        client.disconnect();
                    }
                    for (String parameter : parameters.keySet()) {
                        request.addHeader(parameter, parameters.get(parameter));
                    }
                    ResponseHandler<String> handler = new BasicResponseHandler();
                    String result = null;
                    try {
                        result = httpclient.execute(request, handler);
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    httpclient.getConnectionManager().shutdown();
                    return result;
                }
            }.execute(url).get();

            try {
                return new JSONElement(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public String GET(String type) {
        HttpURLConnection con = null;
        String success = "Failed";
        try {
            con = getHttpConnection(url, type);
            System.out.println(con.getURL());
            con.connect();
            System.out.println(con.getContentLength());
            if (con.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + con.getResponseCode());
            }else{
                success = "Success";
            }
            /*
            BufferedReader br = new BufferedReader(new InputStreamReader(
			(con.getInputStream())));

		    String output;
		    System.out.println("Output from Server .... \n");
		    while ((output = br.readLine()) != null) {
			    System.out.println(output);
		    }
*/
		    //con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.wtf("Error URL", "connection i/o failed");
        }
        return success+" URL:"+con.getURL();
    }

    public HttpURLConnection getHttpConnection(String url, String type){
        URL uri;
        HttpURLConnection con = null;
        try{
            uri = new URL(url);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cache.univ-lille1.fr", 3128));
            con = (HttpURLConnection) uri.openConnection(proxy);
            System.out.println(con.getURL());
            con.setRequestMethod(type); //type: POST, PUT, DELETE, GET
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setConnectTimeout(60000); //60 secs
            con.setReadTimeout(60000); //60 secs
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Accept-Encoding", "utf-8");
            System.out.println(con.toString());
        }catch(Exception e){
            Log.wtf("Error URL","connection i/o failed");
        }
        return con;
    }

}
