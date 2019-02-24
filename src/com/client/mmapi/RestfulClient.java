/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi;


/**
 *
 * @author brado
 * SAMPLE JAVE CLIENT TO ACCESS MMAPI
 */
public class RestfulClient {

    public RestfulClient() {
        RestfulController control = new RestfulController();
        control.init();
        RestfulView rv = new RestfulView();
        rv.setContoller(control);
        rv.runTests();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RestfulClient restfulClient = new RestfulClient();
    }
    
}
