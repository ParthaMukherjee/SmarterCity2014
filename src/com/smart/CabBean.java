package com.smart;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.smart.model.Cab;

@ManagedBean(name="cabbean")
@ViewScoped
public class CabBean {
	
	
	
	public List<Cab> getCabs() {
		
		Cab c1= new Cab("Meru Cab","www.merucab.com","kol-32","50","9834567289");
		Cab c2= new Cab("Calcutta Cab","www.calcab.com","kol-39","25","7865231456");
		Cab c3= new Cab("First Cab","www.firstcab.com","kol-124","45","983721992");
		Cab c4= new Cab("Jamuna Travel","www.jamunat.com","kol-372","35","7890541321");
		Cab c5= new Cab("Airport Cab","www.aircab.com","kol-312","55","9834221355");
		
		cabs.add(c1);
		cabs.add(c2);
		cabs.add(c3);
		cabs.add(c4);
		cabs.add(c5);
		return cabs;
	}

	public void setCabs(List<Cab> cabs) {
		this.cabs = cabs;
	}

	List<Cab> cabs = new ArrayList<Cab>();
	
	public String showCabDetails(){
		
		return "smartcabdetails";
	}

}
