/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mmapi;

import com.client.mmapi.utils.ConfigRestful;
import com.client.mmapi.utils.NameValuePairs;
import com.client.mmapi.utils.TestException;
import com.client.mmapi.webgateway.UIResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brado
 * Set up the view and interaction with the test
 * 
 */
public class RestfulView {
    private RestfulController control;
    
    public RestfulView() {
        control=null;
    };
    /**
     * 
     * @param control 
     */
    public void setContoller(RestfulController control) {
        this.control=control;
    }
    
    /**
     * Run a test as described in the API documentation.
     * The test will keep recursing until Stop is entered
     */
    public void runTests()  {
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String accStr="";  

            System.out.println("Entry Format: apimessage,parmName=parmValue,parmName=parmValue \r\n");
            System.out.println("Enter API Message with Data.  Enter Stop to quit: \r\n");
            try { 
                accStr = br.readLine();
                if (accStr.toLowerCase().equalsIgnoreCase("stop"))
                    break;
                String[] sa = accStr.split(",");
                String mess = sa[0];
                mess=mess.toLowerCase();
                HashMap<String,String> dataMap = new HashMap();
                for (int x=1; x<sa.length;x++) {
                    String dtPairs=sa[x];
                    String dt[] = dtPairs.split("=");
                    dataMap.put(dt[0], dt[1]);

                }
                runApiTest(mess, dataMap);
                
            } catch (IOException ex) {
                System.out.print(ex.getMessage());
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.print("Error parsing Entry Data");
            }
        }
    }
    
    
    private void runApiTest(String apiMessage,HashMap<String,String> dataMap) {
        NameValuePairs np = new NameValuePairs();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {   
            String key = entry.getKey();
            String value = entry.getValue();
            np.addPair(key, value);
        }    
        
        switch (apiMessage) {
            case "api_login":
                np.add(ConfigRestful.getCredentials());
                np.addPair("msg_id", "api_login");
                // reply type to use for this login session
                np.addPair("reply_type", ConfigRestful.getReplyType());
            break;    
            case "api_ping":
                np.addPair("msg_id", "api_ping");
            break;   
            case "api_facility_name":
                np.addPair("msg_id", "api_facility_name");
            break; 
            case "api_facility_name_status":
                np.addPair("msg_id", "api_facility_name_status");
            break; 
            case "api_facility_id":
                np.addPair("msg_id", "api_facility_id");
            break;    
            case "api_patient_name":
                np.addPair("msg_id", "api_patient_name");
            break;    
            case "api_patient_id":
                np.addPair("msg_id", "api_patient_id");
            break;
            case "api_corp_facility_id":
                np.addPair("msg_id", "api_corp_facility_id");
            break;
            case "api_corp_facility_name_status":
                np.addPair("msg_id", "api_corp_facility_name_status");
            break;
            case "api_corp_facility_name":
                np.addPair("msg_id", "api_corp_facility_name");
            break;
            case "api_dispatch_region_list":
                np.addPair("msg_id", "api_dispatch_region_list");
            break;
            case "api_dispatch_region_id":
                np.addPair("msg_id", "api_dispatch_region_id");
            break;
            case "api_referring_id":
                np.addPair("msg_id", "api_referring_id");
            break;
            case "api_referring_name":
                np.addPair("msg_id", "api_referring_name");
            break;
            case "api_referring_name_status":
                np.addPair("msg_id", "api_referring_name_status");
            break;
            case "api_provider":
                np.addPair("msg_id", "api_provider");
            break;
            case "api_invoice_num":
                np.addPair("msg_id", "api_invoice_num");
            break;
            case "api_tech_id":
                np.addPair("msg_id", "api_tech_id");
            break;
            case "api_tech_list_name_status":
                np.addPair("msg_id", "api_tech_list_name_status");
            break;
            case "api_claim_id":
                np.addPair("msg_id", "api_claim_id");
            break;
            case "api_claim_inv_num":
                np.addPair("msg_id", "api_claim_inv_num");
            break;
            case "api_claim_detail":
                np.addPair("msg_id", "api_claim_detail");
            break;
            case "api_exam_id":
                np.addPair("msg_id", "api_exam_id");
            break;
            case "api_exam_patient_date_range":
                np.addPair("msg_id", "api_exam_patient_date_range");
            break;
            case "api_timestamp_data":
                np.addPair("msg_id", "api_timestamp_data");
            break;
            case "api_cpt_code":
                np.addPair("msg_id", "api_cpt_code");
            break;
            
            case "api_logout":
                np.addPair("msg_id", "api_logout");
            break;    
        }
        
        
        
        try {
            execute(np,apiMessage);
        } catch (TestException ex) {
            Logger.getLogger(RestfulView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Call the controller to execute the test.
     * @param np
     * @param apiMessage
     * @throws TestException 
     */
    private void execute(NameValuePairs np,String apiMessage) throws TestException {
        UIResponse response=null;
        response = control.executeTest(np);
        viewResponse(response,apiMessage);
    }
    
    public void viewResponse(UIResponse response,String apiMessage) {
        String replyType = ConfigRestful.getReplyType();
        switch (replyType) {
            case "json":
            case "xml":
                RestfulStringResults strResults = new RestfulStringResults();
                displayResults(strResults,response,apiMessage);
            break;    
        }
    }
    
    private void displayResults(RestfulStringResults strResults,UIResponse response,String apiMessage) {
        strResults.display(response, apiMessage);
        
    }
    
}
