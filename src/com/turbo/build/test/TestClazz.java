package com.turbo.build.test;

import java.util.Iterator;
import java.util.Map;

import org.apache.bcel.classfile.Method;

import com.turbo.build.opt.ClassPathParser;
import com.turbo.build.util.Clazz;
import com.turbo.build.util.Jar;
import com.turbo.build.util.JarUtil;

public class TestClazz {

	public static void main(String[] args) {
		ClassPathParser parser = new ClassPathParser("test-cases\\case3\\App\\.classpath");

		parser.extractJars();

		JarUtil util = new JarUtil(parser.getEntries());
		
		Clazz[] clist = new Clazz[2];
		int n = 0;
		for(Jar jar : util.getJars()) {
			System.out.println(jar.getFullname());
			Map<String, Clazz> map = jar.getClazzes();
			Iterator<String> iter = map.keySet().iterator();
			while(iter.hasNext()) {
				Clazz clazz = map.get(iter.next());
				clist[n++] = clazz;
				System.out.println(clazz);
				for(Method m : clazz.getMethods()) {
					System.out.println("\t" + m.getName());
				}
			}
		}
		
		System.out.println(clist[0].comapreTo(clist[1]));
		System.out.println(clist[1].comapreTo(clist[0]));
	}

}
