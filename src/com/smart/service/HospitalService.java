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

import com.smart.model.Hospital;
import com.smart.model.Parking;

public class HospitalService {
	String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=9000&types=hospital&key=AIzaSyC_egLXDrTq6kCrsN3kpGPnf5aiRlaAuEQ";
	public List<Hospital> findCityHospital(String latlag,String dist) throws Exception{
		List<Hospital> list = new ArrayList<Hospital>();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		url =url.replaceAll("-33.8670522,151.1957362", latlag);
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
			JsonNode n=res.get("opening_hours");
			JsonNode n2=null;
			if(n!=null){
				n2=n.get("open_now");
			}
			Hospital h =null;
			try{
				h = new Hospital(res.get("name").toString(),res.get("vicinity").toString(),res.get("rating").toString(),n2.toString(),"");
			}catch(Exception e){
				
			}
			if(h!=null){
				list.add(h);
			}
		}
		return list;
	}
}
