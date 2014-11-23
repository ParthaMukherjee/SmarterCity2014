package com.ibm.bluemix.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.smart.service.HospitalService;

public class Test {

	public static void main(String[] args) throws Exception{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=22.57265,88.36389&radius=9000&types=parking&key=AIzaSyC_egLXDrTq6kCrsN3kpGPnf5aiRlaAuEQ";
		HttpGet getRequest = new HttpGet(
			url);
		getRequest.addHeader("accept", "application/json");
 
		HttpResponse response = httpClient.execute(getRequest);
 
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}
		String json = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
		
		JsonNode itemsNode = jsonNode.path("results");
		Iterator<JsonNode> itemItr = itemsNode.getElements();
		while(itemItr.hasNext()){
			JsonNode res=  itemItr.next();
			System.out.println(res.get("name"));
			System.out.println(res.get("vicinity"));
		}
		String dist="4";
		String latlng ="22.57265,88.36389";
		HospitalService hs = new HospitalService();
		hs.findCityHospital(latlng, dist);
 
		httpClient.getConnectionManager().shutdown();

	}

}
