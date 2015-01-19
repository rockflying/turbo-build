package com.turbo.build.cg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassParser;

import com.turbo.build.util.IMethod;
import com.turbo.build.util.Jar;
import com.turbo.build.util.ProjectInfo;

public class CallGraph {

	private static List<Jar> jars = null;

	private static List<String> clazzList = null;

	//methods in project classes
	private static List<IMethod> methods = new ArrayList<IMethod>();

	public static List<Jar> getJars() {
		return jars;
	}

	public static List<String> getClazzList() {
		return clazzList;
	}

	public static List<IMethod> getMethods() {
		return methods;
	}

	public static void buildCallGraphFromJars(List<Jar> jars) {
		if (null == jars) {
			return;
		}
		cleanup();
		CallGraph.jars = jars;

		try {
			ClassParser cp;
			for (Jar jarfile : jars) {

				File f = new File(jarfile.getPath());

				if (!f.exists()) {
					System.err.println("Jar file " + jarfile.getPath()
							+ " does not exist");
				}

				JarFile jar = new JarFile(f);

				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					if (entry.isDirectory())
						continue;

					if (!entry.getName().endsWith(".class"))
						continue;

					cp = new ClassParser(jarfile.getPath(), entry.getName());
					ClassVisitor visitor = new ClassVisitor(cp.parse());
					visitor.start();
				}

				jar.close();
			}
		} catch (IOException e) {
			System.err.println("Error while processing jar: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static void buildCallGraphFromClasses(List<String> clazzList) {
		if (null == clazzList) {
			return;
		}
		cleanup();
		CallGraph.clazzList = clazzList;
		ClassParser cp;

		for (String clazz : clazzList) {
			try {
				cp = new ClassParser(clazz);
				ClassVisitor visitor = new ClassVisitor(cp.parse());
				visitor.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void addMethod(IMethod method) {
		if(!methods.contains(method)){
			methods.add(method);
		}
	}
	
	public static void addCallee(IMethod caller, IMethod callee) {
		if (!methods.contains(caller)) {
			return;
		}

		List<IMethod> list = methods.get(methods.indexOf(caller)).getCallees();
		if (!list.contains(callee)) {
			list.add(callee);
		}
	}
	
	private static void cleanup() {
		if (ProjectInfo.changed) {
			// only clear when the project selected changed
			methods.clear();
		}
	}
}
