/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi;

import com.client.mmapi.utils.ConfigRestful;
import com.client.mmapi.utils.NameValuePairs;
import com.client.mmapi.utils.TestException;
import com.client.mmapi.webgateway.Connection;
import com.client.mmapi.webgateway.UIResponse;
import java.io.IOException;

/**
 *
 * @author brado
 * Controls the running of tests
 */
public class RestfulController  {
    private Connection httpCon;
    
    public RestfulController() {
        
    }
    
    /**
     * Set up a connection for the controller to use.
     */
    public void init() {
        httpCon = new Connection();
        httpCon.setReplyType(ConfigRestful.getReplyType());
        httpCon.setURL(ConfigRestful.getContextURL());
    }

    
    
    /**
     * Execute an api test and get the response
     * @param args
     * @return Return a response that can be displayed
     * @throws TestException 
     */
    public UIResponse executeTest(NameValuePairs args) throws TestException{
        String requestProps= args.getParameters();
        UIResponse response;
        try {
            response = httpCon.postRequest(requestProps);
        } catch (IOException ex) {
            throw new TestException(ex);
        }
        return response;
    }
    

    
    
}
