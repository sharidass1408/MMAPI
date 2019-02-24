/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author brado HELPER Methods
 */
public class NameValuePairs {
    private final Properties properties;
    
    public NameValuePairs() {
        properties=new Properties();
    }
    public Properties getProperties() {
        return properties;
    }
    public synchronized void addPair(String name, String value) {
        properties.setProperty(name, value);
    }

    public synchronized void add(NameValuePairs np) {
        //Add in another  NP
        Properties props = np.getProperties();
        Enumeration names = props.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = props.getProperty(name);
            properties.setProperty(name, value);
        }
    }

    public synchronized String getParameters() {
        return encodeString();
    }
    
    private String encodeString() {
        StringBuilder buf = new StringBuilder();
        Enumeration names = properties.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = properties.getProperty(name);
            try {
              String nm = URLEncoder.encode(name,"UTF-8");
              String val = URLEncoder.encode(value,"UTF-8");
              buf.append(nm).append("=").append(val);
              if (names.hasMoreElements()) buf.append("&");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return buf.toString();
    }
    
    @Override
    public String toString() {
        return getParameters();
    }
}
