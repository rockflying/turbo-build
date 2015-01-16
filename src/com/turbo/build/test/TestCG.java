package com.turbo.build.test;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

import com.turbo.build.cg.CallGraph;
import com.turbo.build.cg.ClassVisitor;
import com.turbo.build.opt.ClassPathParser;
import com.turbo.build.util.JarUtil;

public class TestCG {
	
	int b;
	
	public String name;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathParser parser = new ClassPathParser("test-cases\\case4\\YellowBook1\\.classpath");
		parser.extractJars();
		JarUtil util = new JarUtil(parser.getEntries());
//		System.out.println(util.getJars());
		
		CallGraph cg = new CallGraph(util.getJars(), null);
		cg.buildCallGraph();
		
		System.out.println("--------------------------------------");
		try {
			JavaClass clazz = Repository.lookupClass("com.turbo.build.test.TestCG");
			ClassVisitor visitor = new ClassVisitor(clazz);
			visitor.start();
			
			for(Field f : clazz.getFields()) {
				System.out.println(f.toString());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		System.out.println(loader);
		System.out.println(Object.class.getClasses().length);
		try {
			loader.loadClass("");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
