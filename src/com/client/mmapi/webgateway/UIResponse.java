/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi.webgateway;
/**
 *
 * @author brado
 * Response class to hold POST reply
 */
public class UIResponse {
    private int status;
    private String message;
    private String conntext_id;
    private Object data;

    public UIResponse() {
        status=0;
        message="";
        conntext_id="";
        data=null;        
    }
    public int getStatusCode() {
        return status;
    }

    public void setStatusCode(int status) {
        this.status=status;
    }
    
    public String getResponseMessage() {
        return message;
    }

    public void setResponseMessage(String message) {
        this.message=message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data=data;
    }
    
    
    
    public String getContext_id() {
        return conntext_id;
    }

    public void setContext_id(String conntext_id) {
        this.conntext_id=conntext_id;
    }
    
}
