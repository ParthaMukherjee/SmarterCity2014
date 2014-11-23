package com.smart.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.smart.model.Parking;

public class SearchService {

	String latlng="https://maps.googleapis.com/maps/api/geocode/json?address=calcutta%20road&key=AIzaSyC_egLXDrTq6kCrsN3kpGPnf5aiRlaAuEQ";
	String parking="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=22.57265,88.36389&radius=9000&types=parking&key=AIzaSyC_egLXDrTq6kCrsN3kpGPnf5aiRlaAuEQ";
	
	public List<Parking> getCarParkings(String latlong,String dist) throws Exception{
		List<Parking> list = new ArrayList<Parking>();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=22.57265,88.36389&radius=9000&types=parking&key=AIzaSyC_egLXDrTq6kCrsN3kpGPnf5aiRlaAuEQ";
		url =url.replaceAll("22.57265,88.36389", latlong);
		url =url.replaceAll("9000", new String(""+Integer.parseInt(dist)*2000));
		HttpGet getRequest = new HttpGet(url);
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
			Parking p = new Parking("Google Live Data", "Open", res.get("vicinity").toString(),res.get("name").toString(), "50");
			list.add(p);
		}
		
		return list;
	}
	
	
	
	public String getlatlng(String street) throws Exception{
		
		String result="";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url =latlng;
		url=url.replace("calcutta%20road", street);
		HttpGet getRequest = new HttpGet(url);
		getRequest.addHeader("accept", "application/json");
 
		HttpResponse response = httpClient.execute(getRequest);
		
		String json = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
		
		JsonNode itemsNode = jsonNode.path("results");
		Iterator<JsonNode> itemItr= itemsNode.getElements();
		while(itemItr.hasNext()){
			JsonNode res=  itemItr.next();
			JsonNode res2 =res.path("geometry");
			JsonNode res3=  res2.get("location");
			result=res3.get("lat").toString();
			result = result+","+res3.get("lng").toString();
			System.out.println(result);
			break;
		}
 
		httpClient.getConnectionManager().shutdown();
		return result;
	}
}
