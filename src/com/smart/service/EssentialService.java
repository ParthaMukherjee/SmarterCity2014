package com.smart.service;

import java.util.List;

import com.smar.dataaccess.EssentialDAO;
import com.smart.model.Essential;

public class EssentialService {
  EssentialDAO essDAO = new EssentialDAO();
  
  public void createEssential(Essential e){
	  try {
		essDAO.createEssential(e);
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  }
  
  public List<Essential> retrieveEssentials(String pin,String type){
	  try {
		return essDAO.findAllEssentials(pin,type);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return null;
  }
}
