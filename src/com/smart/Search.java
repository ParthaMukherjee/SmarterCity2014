package com.smart;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;

import com.smart.model.Essential;
import com.smart.model.Parking;
import com.smart.service.EssentialService;
import com.smart.service.SearchModel;
import com.smart.service.SearchService;

@ManagedBean(name="mysearch")
@SessionScoped
public class Search {
	
	SearchService se= new SearchService();
	EssentialService es= new EssentialService();
	List<Essential> list = new ArrayList<Essential>();
	List<Parking> listp = new ArrayList<Parking>();
	String livecount;
	
	public String getLivecount() {
		return livecount;
	}

	public void setLivecount(String livecount) {
		this.livecount = livecount;
	}

	public List<Essential> getList() {
		return list;
	}

	public List<Parking> getListp() {
		
		return listp;
	}

	public void setListp(List<Parking> listp) {
		this.listp = listp;
	}

	public void setList(List<Essential> list) {
		this.list = list;
	}

	private boolean  listren=false;
	public boolean getListren() {
		return listren;
	}

	public void setListren(boolean listren) {
		this.listren = listren;
	}

	String pin,search,dist;
	
	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String showSearch(){
		return "smartsearch";
	}
	
	public String doSearch(){
		setListren(true);
		setList(es.retrieveEssentials(getPin(),getSearch()));
		return "smartsearch";
	}
	
	public String doPSearch(){
		setListp(populateParking(new ArrayList<Parking>()));
		return "smartparkingsearch";
	}

	private List<Parking> populateParking(List<Parking> list) {
		String avaiable="5", status="Open", street="44 Park Street", landmark="Infinity Building", capacity="50";
		Parking p1 = new Parking(avaiable, status, street, landmark, capacity);
		avaiable="7";
		status="Open";
		street="2/3 Petroriya Street";
		landmark="Beside ABC Hotel";
		capacity="30";
		Parking p2 = new Parking(avaiable, status, street, landmark, capacity);
		
		avaiable="0";
		status="Closed";
		street="17 Little Rus Street";
		landmark="Near ABC School";
		capacity="60";
		Parking p3 = new Parking(avaiable, status, street, landmark, capacity);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		if("5".equalsIgnoreCase(getDist())){
			avaiable="45";
			status="Open";
			street="17 Russel Street";
			landmark="Near YSS School";
			capacity="66";
			Parking p4 = new Parking(avaiable, status, street, landmark, capacity);
			avaiable="50";
			status="Open";
			street="150 Jessore Road";
			landmark="Near Airport ";
			capacity="100";
			Parking p5 = new Parking(avaiable, status, street, landmark, capacity);
			list.add(p4);
			list.add(p5);
		}
		
		try {
			List<Parking> temp =se.getCarParkings(se.getlatlng(getSearch()),getDist());
		
			if(temp!=null){
				list.addAll(temp);
				setLivecount(""+temp.size());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}

}
