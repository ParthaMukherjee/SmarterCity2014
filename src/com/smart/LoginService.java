package com.smart;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
	
	public static Map<String, String> getMap() {
		return map;
	}

	public static void setMap(Map<String, String> map) {
		LoginService.map = map;
	}

	private static Map<String, String> map;
	
	 static {
		
		map = new HashMap<String, String>();
		map.put("admin", "admin");
		map.put("nabinverse", "welcome");
		map.put("pmu20", "welcome2ibm");
		map.put("guest", "guest");
	}

}
