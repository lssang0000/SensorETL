package com.ssu.ss.ssu_ss_sensoretl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Loader {
	
	private String url;
	
	public void printResult(int res){
		
		switch(res){
		case 0:	System.out.println("DONE"); break;
		case -22: System.out.println("C_ERROR_INDEX_UPPERCASE"); break;
		}
	}
	
	public Loader(){
		this.url = "http://192.168.232.141:8080/ss/load/post/";
	}
	
	public Loader(String host) {
		// TODO Auto-generated constructor stub
		this.url = "http://"+host+":8080/ss/load/post/";
	}
	
	public boolean isJSONValid(String jsonStr) {
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
	
	public boolean isAllLower(String str){
		return str.matches("^[a-z]*$");
	}
	
	public int load(int repoType, String[] index, JSONObject data){
		//check repotype
		
		//check index
		if(repoType == Constants.REPO_TYPE_ELASTICSEARCH  
				&& !isAllLower(index[0])){
			System.err.println("[ss-sensorETL]\'index[0]\' of Elasticsearch must be lowercase only.");
			return Constants.C_ERROR_INDEX_UPPERCASE;
		}
				
		//send post request
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		switch(repoType){
			case Constants.REPO_TYPE_ELASTICSEARCH:
				params.add(new BasicNameValuePair("repoType", "elasticsearch"));	break;
			default:
				params.add(new BasicNameValuePair("repoType", "none"));	break;
		}		
		params.add(new BasicNameValuePair("index1", index[0]));
		params.add(new BasicNameValuePair("index2", index[1]));
		if(index.length>2){
			//...
		}
		params.add(new BasicNameValuePair("dataStr", data.toString()));

		
		try { 
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} 
		catch (UnsupportedEncodingException e) 
		{ // writing error to Log 
			e.printStackTrace(); 			
		}

		try { 
			HttpResponse response = httpClient.execute(httpPost); 
			HttpEntity respEntity = response.getEntity();
			if (respEntity != null) 
			{ // EntityUtils to get the reponse content 
				String content = EntityUtils.toString(respEntity); 
				//System.out.println("res : "+content);
			}
		}
		catch (ClientProtocolException e) { 
			// writing exception to log 
			e.printStackTrace();
		} 
		catch (IOException e) { 
			// writing exception to log 
			e.printStackTrace(); 
		}		
		
		return Constants.DONE;
	}
	
//	public void testGet() throws Exception{
//		
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://203.253.23.46:8080/ss/load/get?data=d");
//        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
// 
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = reader.readLine()) != null) {
//            response.append(inputLine);
//        }
//        
//        reader.close();
//        httpClient.close();
//        
//	}
	
}
