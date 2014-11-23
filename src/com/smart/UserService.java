package com.smart;

import com.mongodb.BasicDBObject;
import com.smar.dataaccess.UserDAO;
import com.smart.model.User;

public class UserService {
	
	private UserDAO userDAO = new UserDAO();
	
	
	public User getUser(String userID){
		
		User u= new User();
		u.setUser(userID);
		BasicDBObject bo=null;
		try {
			bo= userDAO.getUser(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bo!=null){
			u.setPasscode(bo.getString("Passcode"));
			u.setName(bo.getString("Name"));
		}
		return u;
	}
	
public void createUser(User user){
		
		try {
			 userDAO.createUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
