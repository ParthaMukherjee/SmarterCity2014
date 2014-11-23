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
import com.smart.model.Task;
import com.smart.model.User;

public class UserDAO{
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
	static String dbCollectionName = "User";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5761861843295817278L;

	public UserDAO(){
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
				//throw new Exception("Key is null");
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
	
	
	public BasicDBObject createUser(User user) throws Exception{
		
		DB _db = getConnection();
		// get a DBCollection object
		try {
			System.out.println("Creating Collection");
			DBCollection col = _db.getCollection(dbCollectionName);
			// create a document
			BasicDBObject json = new BasicDBObject();
			json.append("Name", user.getName());
			json.append("Email", user.getEmail());
			json.append("Passcode", user.getPasscode());
			json.append("Pin", user.getPin());
			json.append("User", user.getUser());
			// insert the document
			col.insert(json);
			System.out.println("Task inserted..");
			
			return json;
		}
		catch(Exception e){
			throw e;
		}
		}
	

	
	public  BasicDBObject getUser(User user) throws Exception{	
		try{
			System.out.println("starting object read..");
			DB _db = getConnection();
			BasicDBObject query = new BasicDBObject();
			String userID =user.getUser();
			if( (userID!=null) && (userID !="") ) 			query.append("User", userID);
			System.out.println("Querying for: " + query);
			DBCollection col = _db.getCollection(dbCollectionName);
			DBCursor cursor = col.find(query);
			BasicDBObject obj=null;
			while (cursor.hasNext()) {
					obj =(BasicDBObject) cursor.next();
					System.out.println("Retrieved: " + obj);
			}
			cursor.close();
			return obj;
		}catch(Exception e){
			throw e;
		}
	}
	
	public  List<BasicDBObject> findTasks() throws Exception{	
		try{
			System.out.println("starting object read..");
			List<BasicDBObject> list = new ArrayList<BasicDBObject>();
			DB _db = getConnection();
			BasicDBObject query = new BasicDBObject();
			System.out.println("Querying for: " + query);
			DBCollection col = _db.getCollection(dbCollectionName);
			DBCursor cursor = col.find();
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

	public List<Task> findAllTasks() throws Exception{
		List<BasicDBObject> lists = findTasks();
		List<Task> retVals = new ArrayList<Task>();
		for(BasicDBObject bo:lists){
			Task t = new Task();
			t.setTaskId(bo.getString("TaskId"));
			t.setAssigned(bo.getString("Assigned"));
			t.setCreatedBy(bo.getString("CreatedBy"));
			t.setTaskName(bo.getString("TaskName"));
			t.setCreated(new Date(bo.getString("Created")));
			//t.setCompletionDate(bo.getString("CompletionDate"));
			t.setPriority(bo.getString("Priority"));
			t.setStatus(bo.getString("Status"));
			t.setTaskDescription(bo.getString("TaskDescription"));
			retVals.add(t);
		}
		return retVals;
		
	}
	
	private BasicDBObject updateRecord(String name, String age, String address) throws Exception{
	try{
		System.out.println("starting object update.." + name + age + address);
		DB _db = getConnection();
		BasicDBObject query = new BasicDBObject();
		query.append("name", name);
		System.out.println("Updating Record: " + query);
		DBCollection col = _db.getCollection(dbCollectionName);
		BasicDBObject updates = new BasicDBObject();
		if( (name!=null) && (name !="") ) 			updates.append("name", name);
		if( (age!=null) && (age !="") )				updates.append("age", age);
		if( (address!=null) && (address !="") )		updates.append("address",address);
		System.out.println("Querying for update: " + query );
	
		
		
			
		col.update(query,updates);
		System.out.println("col after update" + col.toString() + col.getCount());
		return updates;
	}catch(Exception e){
		throw e;
	}
	}
	
	public BasicDBObject deleteTask(Task task) throws Exception{
		try{
			System.out.println("starting object delete..");
			DB _db = getConnection();
			BasicDBObject query = new BasicDBObject();
			String taskId=task.getTaskId();
			if( (taskId!=null) && (taskId !="") ) 			query.append("TaskId", taskId);
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
