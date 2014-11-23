package com.smart.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;

import com.smart.model.Essential;

public class SearchModel extends DataModel<Essential>{

	List<Essential> list =new ArrayList<Essential>();
	
	int rowIndex=0;
	public List<Essential> getList() {
		return list;
	}

	public void setList(List<Essential> list) {
		this.list = list;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Essential getRowData() {
		// TODO Auto-generated method stub
		return list.get(rowIndex);
	}

	@Override
	public int getRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getWrappedData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRowAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRowIndex(int arg0) {
		rowIndex=arg0;
	}

	@Override
	public void setWrappedData(Object arg0) {
		// TODO Auto-generated method stub
		
	}

}
