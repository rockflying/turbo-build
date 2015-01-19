package com.turbo.build.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Clazz {
	private Method[] methods;
	private Field [] fields;
	private String   name;
	
	public Clazz(JarClassLoader loader, String strclazz) {
		name = strclazz;
		try {
			Class<?> clazz = loader.loadClass(strclazz);
//			System.out.println(clazz.getMethods());
			methods = clazz.getMethods();
			fields  = clazz.getFields();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
