package com.turbo.build.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.generic.Type;

public class IMethod {
	String name;
	Type[] parameters;
	Type ret;

	String clazz;
	String jar; // TODO can be moved into clazz

	List<IMethod> callees = new ArrayList<IMethod>();

	public IMethod(String clazz, String name, Type[] types, Type ret) {
		this.clazz = clazz;
		this.name = name;
		this.parameters = types;
		this.ret = ret;
	}

	public List<IMethod> getCallees() {
		return callees;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = clazz + ":" + name + "(";
		for (int i = 0; i < parameters.length; ++i) {
			str += parameters[i].toString();
			if (i < parameters.length - 1) {
				str += ",";
			}
		}
		return str + ")";
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return toString().equals(obj.toString());
	}

	public void setParameters(Type[] types) {
		this.parameters = types;
	}
}
