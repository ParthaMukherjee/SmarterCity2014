package com.smart;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.smart.model.Hospital;
import com.smart.service.HospitalService;
import com.smart.service.SearchService;
@ManagedBean(name="hospital")
@SessionScoped
public class HospitalBean {
	
	List<Hospital> listh = new ArrayList<Hospital>();
	
	String search;
	String dist;
	String livecount;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getLivecount() {
		return livecount;
	}

	public void setLivecount(String livecount) {
		this.livecount = livecount;
	}

	public List<Hospital> getListh() {
		return listh;
	}

	public void setListh(List<Hospital> listh) {
		this.listh = new ArrayList<Hospital>();
		this.listh.addAll(listh);
	}
	
	public String doSearch(){
		HospitalService hs = new HospitalService();
		SearchService s = new SearchService();
		String latlng;
		try {
			latlng = s.getlatlng(getSearch());
			List<Hospital> hospitals =hs.findCityHospital(latlng, getDist());
			setLivecount(""+hospitals.size());
			setListh(hospitals);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "smarthospitalsearch";
	}
}
