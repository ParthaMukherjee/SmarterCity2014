package com.smart.service;
 
import java.lang.reflect.Field;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.smart.model.Task;
 
public class LazySorter implements Comparator<Task> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(Task task1, Task task2) {
        try {
        	Object []arr = Task.class.getDeclaredFields();
        	Field obj =Task.class.getDeclaredField(sortField);
        	obj.setAccessible(true);
            Object value1 = obj.get(task1);
            Object value2 = obj.get(task2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}