package com.ssu.ss.ssu_ss_sensoretl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.text.AbstractDocument.Content;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
//    	String jsonStr = "{" +
//    	        "\"user\":\"kimchy\"," +
//    	        "\"postDate\":\"2013-01-30\"," +
//    	        "\"message\":\"trying out Elasticsearch\"" +
//    	    "}";
//    	
//    	Boolean b = isJSONValid(null);
//    	
//    	System.out.println(b);
    	
//    	String str = "ac";    	
//    	System.out.println(str.matches("^[a-z]*$"));
//    	System.out.println(str.matches("^[A-Z]*$"));
    	
        Loader loader = new Loader("192.168.232.141");
        
        System.out.println("!");
        
        String[] index = {"myindex","myDocType"};
//        for(int i = 0 ; i < 10 ; i ++){
    	JSONObject json = new JSONObject();
        json.put("name", "lssang");
        json.put("age", 29);
        json.put("regtime", System.currentTimeMillis());
        System.out.println("!!");
        int res = loader.load(Constants.REPO_TYPE_ELASTICSEARCH, index, json);
        System.out.println("!!!");
        loader.printResult(res);
        System.out.println("!!!!");
//      }
    }
    
    public static boolean isJSONValid(String jsonStr) {
		 try {
		        new JSONObject(jsonStr);
		    } catch (JSONException ex) {
		        try {
		            new JSONArray(jsonStr);
		        } catch (JSONException ex1) {
		            return false;
		        }
		    }
		    return true;
	}
}
