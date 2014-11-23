package com.ibm.bluemix.demo.util;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Timestamp;
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

@WebServlet("/memberinfo")
public class BluemixDemoServlet extends HttpServlet {
	static String databaseHost = "localhost";
	static String port = "50000";
	static String databaseName = "jsondb";
	static String username = "myuser";
	static String password = "mypass";
	static String databaseUrl = "type://hostname:port/dbname";
	//static String databaseUrl = "mongodb://IbmCloud_ep29clhq_ceicsk3c_rc19kjtl:gz7Dfvlyp2woBTWEHDthDRo20X1d3Hpm@ds041180.mongolab.com:41180/IbmCloud_ep29clhq_ceicsk3c";
	static String thekey = null;
	static boolean dbSetup = false;
	static String dbCollectionName = "MemberInfo";
	
	private void dbSetup(PrintWriter writer) throws Exception{
		
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
				throw new Exception("Key is null");
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
			
			writer.println("username:"+username+" password:"+ password +" databaseName:"+ databaseName + "port:"+port);
			dbSetup = true;
		} else {
			//throw new Exception("VCAP_SERVICES is null");
		}

	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5761861843295817278L;

	public BluemixDemoServlet(){
		super();
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, java.io.IOException {
		//set request and response configuration
		System.out.println("Inside do Get");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		
		PrintWriter writer = response.getWriter();
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String address = request.getParameter("address");
		String operation = request.getParameter("operation");
		int operint = 0;
		
		System.out.println("name is:"+ name );
		System.out.println("age is:"+ age);
		System.out.println("address is:"+ address);
		System.out.println("operation is:"+ operation);	
		
		//response.getWriter().write("<name>swanand</name>");
		
		writer.println("Servlet: " + this.getClass().getName());
		writer.println();
		writer.println("VCAP Host: " + System.getenv("VCAP_APP_HOST") + ":"
				+ System.getenv("VCAP_APP_PORT"));
		writer.println("Host IP: "
				+ InetAddress.getLocalHost().getHostAddress());
		
		System.out.println("operation with which servlet called"+operation);
		if (operation ==null) {
			operation = "Create";
			operint = 1;
				
		}
		else if (operation.equalsIgnoreCase("Create")){
			operint = 1;
		}
		else if (operation.equalsIgnoreCase("Update")){
			operint = 2;
		}
		else if (operation.equalsIgnoreCase("Delete")){
			operint = 3;
		}
		try{
			this.dbSetup(writer);
			System.out.println("Value of operint:" +operint);
			switch (operint){
				case 1:{
					BasicDBObject returnedObj = this.createRecord(name,age,address);
					if(returnedObj !=null){
						System.out.println("record created");
						operation = "Create";
						//memberList = new MemberList(returnedObj);
					}
					break;
				}
				case 2:{
					BasicDBObject returnedObj = this.updateRecord(name,age,address);
					if(returnedObj!=null){
						System.out.println("record updated" + name);
						operation = "Update";
						//memberList = new MemberList(returnedObj);
					}
					break;
				}
				case 3:{
					BasicDBObject returnedObj = this.deleteRecord(name,age,address);
					if(returnedObj!=null) System.out.println("record deleted");
					operation = "Delete";
					break;
				}
			}
			DB _db = this.getConnection();
			// get a DBCollection object
			System.out.println("Creating Collection");
			DBCollection col = _db.getCollection(dbCollectionName);
			//System.out.println("col contents: " +col.toString());
			request.setAttribute("memberCollection", col);
			request.setAttribute("operation", operation);
			RequestDispatcher view = request.getRequestDispatcher("memberinfo.jsp");
			view.forward(request, response);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}

	
	private DB getConnection() throws Exception{
	try{
		MongoClient mongoClient;
		DB db = null;
		System.out.println("port" +port);
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
	private BasicDBObject createRecord(String name, String age, String address) throws Exception{
		
	DB _db = getConnection();
	// get a DBCollection object
	try {
		System.out.println("Creating Collection");
		DBCollection col = _db.getCollection(dbCollectionName);

		// create a document
		BasicDBObject json = new BasicDBObject();
		json.append("name", name);
		json.append("age", age);
		json.append("address", address);
		//json.append("timestamp", timestampForInsert);


		// insert the document
		col.insert(json);
		System.out.println("after insert");
		return json;
	}
	catch(Exception e){
		throw e;
	}
	}
	
	private BasicDBObject readRecord(String name, String age, String address) throws Exception{	
	try{
		System.out.println("starting object read..");
		DB _db = getConnection();
		BasicDBObject query = new BasicDBObject();
		if( (name!=null) && (name !="") ) 			query.append("name", name);
		if( (age!=null) && (age !="") )				query.append("age", age);
		if( (address!=null) && (address !="") )		query.append("address",address);
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
	
	private BasicDBObject deleteRecord(String name, String age, String address) throws Exception{
		try{
			System.out.println("starting object delete..");
			DB _db = getConnection();
			BasicDBObject query = new BasicDBObject();
			if( (name!=null) && (name !="") ) 			query.append("name", name);
			if( (age!=null) && (age !="") )				query.append("age", age);
			if( (address!=null) && (address !="") )		query.append("address",address);
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
