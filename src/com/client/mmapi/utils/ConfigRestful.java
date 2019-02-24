/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi.utils;

/**
 *
 * @author brado
 */
public class ConfigRestful {
    public static String XML="xml";
    public static String JSON="json";
    public static String JAVA_OBJECT="javaobject_v2";
    
    // the server URL where the API is located
    public static String getContextURL() {
        return "https://demo.medimatrix.biz/mmapi/controller";
    }

    // The reply type you what your response to send back
    public static String getReplyType() {
        return "json";
    }
    
    // Helper function for logging into the MMApi
    public static NameValuePairs getCredentials() {
        NameValuePairs np = new NameValuePairs();
        np.addPair("user", "000mmapi");
        np.addPair("password", "BMN06200");
        np.addPair("PIN", "1111");
        return np;
    }
}
