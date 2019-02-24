/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi;

import com.client.mmapi.webgateway.UIResponse;

/**
 *
 * @author brado
 */
public class RestfulStringResults {
    
    public void display (UIResponse response,String apiMessage) {
        StringBuilder b= new StringBuilder();
        b.append("Start Test **** ");
        b.append(apiMessage);
        b.append(" ***************");
        b.append("\r\n");
        b.append("\r\n");
        b.append("CALLER context id = ");
        b.append(response.getContext_id());
        b.append("\r\n");
        b.append("HTTP status code = ");
        b.append(response.getStatusCode());
        b.append("\r\n");
        b.append("HTTP response message = ");
        b.append(response.getResponseMessage());
        b.append("\r\n");
        b.append("Response data = ");
        b.append("\n");
        b.append(response.getData());
        b.append("\r\n");
        b.append("\r\n");
        b.append("End Test ****** ");
        b.append(apiMessage);
        b.append(" ***************");
        b.append("\r\n");
        b.append("\r\n");
        System.out.println(b.toString());
        
    }
    
    
}
