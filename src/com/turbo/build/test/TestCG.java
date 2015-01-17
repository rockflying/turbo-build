package com.turbo.build.test;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

import com.turbo.build.cg.CallGraph;
import com.turbo.build.cg.ClassVisitor;
import com.turbo.build.util.IMethod;

public class TestCG {
	
	int b;
	
	public String name;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ClassPathParser parser = new ClassPathParser("test-cases\\case4\\YellowBook1\\.classpath");
//		parser.extractJars();
//		JarUtil util = new JarUtil(parser.getEntries());
//		
//		CallGraph.buildCallGraphFromJars(util.getJars());
		
		try {
			JavaClass clazz = Repository.lookupClass("com.turbo.build.test.TestCG");
			ClassVisitor visitor = new ClassVisitor(clazz);
			visitor.start();
			
//			for(Field f : clazz.getFields()) {
//				System.out.println(f.toString());
//			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (IMethod m : CallGraph.getMethods()) {
			System.out.println(m);
			for (IMethod callee : m.getCallees()) {
				System.out.println("\t" + callee);
			}
		}
		
		calc(1, "123", false);
	}
	
	public static void calc(int a, String name, boolean b) {
		
	}
}
