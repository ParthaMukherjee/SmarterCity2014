package com.smar.dataaccess;


import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DB;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.BasicDBList;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.smart.model.Essential;
import com.smart.model.Task;
import com.smart.model.User;

public class EssentialDAO{
	/*static String databaseHost = "192.155.243.27";
	static String port = "10012";
	static String databaseName = "db";
	static String username = "67c29bbf-8f24-453c-b38f-fc8a033bebd5";
	static String password = "091c0de2-16ca-4208-a8e5-fc3aa285d9cf";
	//static String databaseUrl = "type://hostname:port/dbname";
	static String databaseUrl = "mongodb://67c29bbf-8f24-453c-b38f-fc8a033bebd5:091c0de2-16ca-4208-a8e5-fc3aa285d9cf@192.155.243.27:10012/db";
	*/
	static String databaseHost = "localhost";
	static String port = "27017";
	static String databaseName = "smart";
	static String username = "67c29bbf-8f24-453c-b38f-fc8a033bebd5";
	static String password = "091c0de2-16ca-4208-a8e5-fc3aa285d9cf";
	static String databaseUrl = "type://hostname:port/dbname";
	static String thekey = null;
	static boolean dbSetup = false;
	static String dbCollectionName = "Essential";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5761861843295817278L;

	public EssentialDAO(){
		super();
		dbSetup();
	}
	
private void dbSetup() {
		
		if(dbSetup) return; //one time variable setting
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		System.out.println("VCAP_SERVICES" +VCAP_SERVICES);

		if (VCAP_SERVICES != null) {
			// parse the VCAP JSON structure
			BasicDBObject obj = (BasicDBObject) JSON.parse(VCAP_SERVICES);
			Set<String> keys = obj.keySet();
			System.out.println("keys" +keys);
			// Look for the VCAP key that holds the JSONFB (formerly IJDS)
			// or MongoDB information, it will pick the last one
			for (String eachkey : keys) {
				// The sample work with the IBM JSON Database as well as MongoDB
				// The JSONDB service used to be called IJDS
				if (eachkey.contains("JSONDB")) {
					thekey = eachkey;
				}
				if (eachkey.contains("mongodb")) {
					thekey = eachkey;
					System.out.println("print" +thekey);
				}
			}
			if (thekey == null) {
			}
            System.out.println("Starting to parse ..");
			// Parsing the parameters out of the VCAP JSON document
            BasicDBList list = (BasicDBList) obj.get(thekey);
			obj = (BasicDBObject) list.get("0");
			System.out.println("got the object" + obj + obj.size() + obj.toString());
			obj = (BasicDBObject) obj.get("credentials");
			System.out.println("got the credentials" + obj.containsField("credentials"));
			databaseHost = (String) obj.get("host");
			System.out.println("got the host" + obj.containsField("host"));
			
			//TODO: IJDS uses "db", Informix uses "database"
			databaseName = (String) obj.get("database");
			System.out.println("got the databaseName" + obj.containsField("database"));
			if (databaseName == null ){
				databaseName = (String) obj.get("db");
				System.out.println("databaseName was null" + obj.containsField("db"));
				
			}
			// the IJDS service has port as an String, MongoDB has it as a
			// Integer, Informix also uses an Integer
			if (obj.get("port") instanceof Integer) {
				port = (String) obj.get("port").toString();
			} else {
				port = (String) obj.get("port");
			}
			
			username = (String) obj.get("username");
			System.out.println("got the username" + obj.containsField("username"));
			password = (String) obj.get("password");
			System.out.println("got the password" + obj.containsField("password"));
			databaseUrl = (String) obj.get("url");
			System.out.println("got the databaseUrl" + obj.containsField("url"));
			
			dbSetup = true;
		} else {
			//throw new Exception("VCAP_SERVICES is null");
		}

	}
	
	private DB getConnection() throws Exception{
	try{
		MongoClient mongoClient;
		DB db = null;
		System.out.println("port " +port);
		mongoClient = new MongoClient(databaseHost, Integer.valueOf(port).intValue());
		db = mongoClient.getDB(databaseName);
		boolean auth = db.authenticate(username, password.toCharArray());
			if (!auth) {
				throw new Exception("Authorization Error");
			} else {
				System.out.println("Authenticated");
			}
		return db;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public BasicDBObject createEssential(Essential e) throws Exception{
		
		DB _db = getConnection();
		// get a DBCollection object
		try {
			System.out.println("Creating Collection");
			DBCollection col = _db.getCollection(dbCollectionName);
			// create a document
			BasicDBObject json = new BasicDBObject();
			json.append("Name", e.getName());
			json.append("Email", e.getEmail());
			json.append("Phone", e.getPhone());
			json.append("Type", e.getType());
			json.append("Pin", e.getPin());
			// insert the document
			col.insert(json);
			System.out.println("E inserted..");
			
			return json;
		}
		catch(Exception ex){
			throw ex;
		}
		}
	

	
	public  List<BasicDBObject> getEsstentials(String pin,String type) throws Exception{	
		try{
			System.out.println("starting object read..");
			List<BasicDBObject> list = new ArrayList<BasicDBObject>();
			DB _db = getConnection();
			BasicDBObject query = new BasicDBObject();
			if( pin!=null && !"".equalsIgnoreCase(pin) ) {
				query.append("Pin", pin);
			}
			if( (type!=null) && (!"".equalsIgnoreCase(type)) ) query.append("Type", type);
			System.out.println("Querying for: " + query);
			DBCollection col = _db.getCollection(dbCollectionName);
			DBCursor cursor = col.find(query);
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
				obj =(BasicDBObject) cursor.next();
				list.add(obj);
				System.out.println("Retrieved: " + obj);
		}
			cursor.close();
			return list;
		}catch(Exception e){
			throw e;
		}
	}
	
	
	public List<Essential> findAllEssentials(String pin,String type) throws Exception{
		List<BasicDBObject> lists = getEsstentials(pin,type);
		List<Essential> retVals = new ArrayList<Essential>();
		for(BasicDBObject bo:lists){
			Essential t = new Essential();
			t.setName(bo.getString("Name"));
			t.setEmail(bo.getString("Email"));
			t.setPhone(bo.getString("Phone"));
			t.setPin(bo.getString("Pin"));
			t.setType(bo.getString("Type"));
			//t.setCompletionDate(bo.getString("CompletionDate"));
			retVals.add(t);
		}
		return retVals;
		
	}
	
	
	
	public BasicDBObject deleteEssential(Essential ess) throws Exception{
		try{
			System.out.println("starting object delete..");
			DB _db = getConnection();
			BasicDBObject query = new BasicDBObject();
			String phone=ess.getPhone();
			if( (phone!=null) && (phone !="") ) 			query.append("Phone", phone);
			System.out.println("Querying for Delete: " + query);
			DBCollection col = _db.getCollection(dbCollectionName);
			DBCursor cursor = col.find(query);
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
					obj = (BasicDBObject)cursor.next();
					System.out.println("Retrieved: " + obj);
			}
			col.remove(query);
			cursor.close();
			return obj;
		}catch(Exception e){
			throw e;
		}
	
	}
	

}//end of class
