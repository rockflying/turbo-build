package com.turbo.build.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class Clazz {
	private Method[] methods;
	private Field [] fields;
	private String   name;
	
	public Clazz(JavaClass clazz) {
		name = clazz.getClassName();
		methods = clazz.getMethods();
		fields  = clazz.getFields();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	public Method[] getMethods() {
		return methods;
	}

	public Field[] getFields() {
		return fields;
	}

	public int getMethodCount() {
		return methods.length;
	}
	
	public int getFieldCount() {
		return fields.length;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public int comapreTo(Clazz obj) {
		// TODO complete this function
		getMethodList();
		getFieldList();
		return 4;
	}
	
	private List<String> getMethodList() {
		List<String> methodList = new ArrayList<String>();
		for (Method m : methods) {
			methodList.add(m.toString());
		}
		return methodList;
	}
	
	private List<String> getFieldList() {
		List<String> fieldList = new ArrayList<String>();
		for(Field f : fields) {
			fieldList.add(f.toString());
		}
		return fieldList;
	}
}
