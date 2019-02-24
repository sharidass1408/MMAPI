/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi.webgateway;

import java.io.CharArrayWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Example of a Rest Call in Java using MMApi
 * @author brado
 */
public class Connection {
    private String contextValue;
    private String contextid;
    private String sURL;
    private String replyType;
    
    /**
     * Set up the client connection
     */
    public Connection() {
        contextValue=null;
        sURL=null;
        replyType=null;
    }
    
    /**
     * Set the URL from the controller to use for the post
     * @param url 
     */
    public void setURL(String url) {
        this.sURL=url;
    }
    
    /**
     * Set the reply type for used for the POST
     * Needs to be sent each time you POST
     * 
     * @param replyType 
     */
    public void setReplyType(String replyType) {
        this.replyType=replyType;
    }

    /**
     * This method connects to the server and make the API call
     * and returns the response
     * @param paramArgs  API parameters
     * @return
     * @throws IOException 
     */
    public synchronized UIResponse postRequest(String paramArgs) throws IOException {
        // Write the arguments as post data
        HttpURLConnection httpCon = openConnection();

        /*
        IMPORTANT: All clients need to send these values in the request.  If you
        do not send them the call will fail.  We also have an AJAX sample
        that show these being sent. 
        */
        httpCon.setRequestProperty("accept", "text/javascript, application/javascript, text/html, application/xml, text/xml, */*");
        httpCon.setRequestProperty("dynamic-reply-type", replyType);
        httpCon.setRequestProperty("reply_type", replyType);
        // set the header so we know it is coming form a WebInterstate Client
        httpCon.setRequestProperty("User-Agent", "WEBIAPP");
        
        /*
        In Java and Perhaps DOT NET the context/session value will be read from the 
        response object 1 time after login is called and the log in is valid.
        After a valid login using JSESSIONID (SEE VALUE SAVE IN RESPONSE BELOW).
        You will need to send this id with each subsequent request until
        you log out.  Logout will destroy the session or context value.
        */
        if (contextValue !=null)
            httpCon.addRequestProperty("Cookie",contextValue);
        DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
        out.writeBytes(paramArgs);
        out.flush();
        out.close();
        return processResponse(httpCon);
    }
    
    
    
    /**
     * You MUST get the JSESSIONID from Set-Cookie value the 
     * FIRST TIME it shows up which is after a valid login.
     * This value needs to be sent with each request.  
     * See above
     * 
     * @param httpCon
     * @return
     * @throws IOException 
     */
    UIResponse processResponse(HttpURLConnection httpCon) throws IOException  {
        UIResponse uiresponse=null;
        String value = httpCon.getHeaderField("Set-Cookie");
        
        if (value != null) {
            if (value.contains("JSESSIONID")) {
                // SAVE THIS VALUE For EACH REQUEST
                this.contextValue=value;
                // Setting this to simply display in the output of the test
                String s[] = value.split(";");
                contextid=s[0];
            }
        }
        
        /*
        Get the reply results from the POST
        */
        uiresponse = getResponse(httpCon,contextid);
        return uiresponse;
    }
    
    
    /**
     * Get the json/xml string response to display to user
     * @param httpCon
     * @param contextid
     * @return
     * @throws IOException 
     */
    
    private UIResponse getResponse(HttpURLConnection httpCon,String contextid) throws IOException  {
        UIResponse uiresponse = new UIResponse();
        
        int status;
        String responseMessage;
        try {
            status = httpCon.getResponseCode();
            responseMessage = httpCon.getResponseMessage();
            uiresponse.setStatusCode(status);
            uiresponse.setResponseMessage(responseMessage);
            
        } catch (IOException e) {
            throw e;
        }
        
        InputStream is=null;
        CharArrayWriter caw=null;
        try {
            is = httpCon.getInputStream();
            Reader inputStreamReader = new InputStreamReader(is); 
            int k = 4096;
            caw = new CharArrayWriter(k);
            char[] ca = new char[k];
            while ( (k = inputStreamReader.read(ca, 0, k)) != -1) {
              caw.write(ca, 0, k);
            }
            caw.flush();
            char[] carray = caw.toCharArray();
            uiresponse.setData(String.valueOf(carray));
        }catch (IOException e) {
            throw e;
        } finally {
            if (caw !=null) {
                caw.close();
            }
            if (is !=null)
                try {
                    is.close();
            } catch (IOException ex) {
            }
            closeConnection(httpCon);
        }
        uiresponse.setContext_id(contextid);

        return uiresponse;
    }
    
    private HttpURLConnection openConnection() throws MalformedURLException, IOException {
        URL url = new URL(sURL);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoInput(true);
        httpCon.setDoOutput(true);
        httpCon.setConnectTimeout(300000);
        httpCon.setReadTimeout(300000);
        httpCon.setUseCaches(false);
        httpCon.setRequestProperty("Content-Type",
                               "application/x-www-form-urlencoded");
        return httpCon;
    }
    
    private void closeConnection(HttpURLConnection httpCon) {
        httpCon.disconnect();
    }
    
}
