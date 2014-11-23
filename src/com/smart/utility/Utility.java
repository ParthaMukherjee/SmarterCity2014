package com.smart.utility;

import javax.el.ELContext;
import javax.faces.context.FacesContext;

public class Utility {

	public static Object getBean(String beanName){
	    Object bean = null;
	    FacesContext fc = FacesContext.getCurrentInstance();
	    if(fc!=null){
	    	ELContext elContext =fc.getELContext();
	         bean = elContext.getELResolver().getValue(elContext, null, beanName);
	    }

	    return bean;
	}
}
