package com.turbo.build.util;

import java.util.ArrayList;
import java.util.List;

public class IMethod {
	String name;
	List<String> parameters = new ArrayList<String>();
	String ret;
	
	String clazz;
	String jar;    // TODO can be moved into clazz
	
	List<IMethod> callees = new ArrayList<IMethod>();

	public IMethod(String clazz, String name) {
		this.clazz = clazz;
		this.name = name;
	}

	public List<IMethod> getCallees() {
		return callees;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return clazz+":"+name;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return toString().equals(obj.toString());
	}
}
