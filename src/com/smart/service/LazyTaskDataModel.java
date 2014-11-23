package com.smart.service;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.smart.model.Task;
 
/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class LazyTaskDataModel extends LazyDataModel<Task> {
     
    private List<Task> datasource;
     
    public LazyTaskDataModel(List<Task> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Task getRowData(String rowKey) {
        for(Task t : datasource) {
            if(t.getTaskId().equals(rowKey))
                return t;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Task t) {
        return t.getTaskId();
    }
 
    @Override
    public List<Task> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
    	List<Task> data = new ArrayList<Task>();
 
        //filter
    	datasource = new TaskService().getAllTasks();
        for(Task car : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field field= Task.class.getDeclaredField(filterProperty);
                        field.setAccessible(true);
                        String fieldValue = String.valueOf(field.get(car));
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(car);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}