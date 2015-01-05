package com.turbo.build.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
}
